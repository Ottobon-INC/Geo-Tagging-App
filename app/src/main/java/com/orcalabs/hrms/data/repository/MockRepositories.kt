package com.orcalabs.hrms.data.repository

import com.orcalabs.hrms.data.mock.MockAttendanceData
import com.orcalabs.hrms.data.mock.MockChatData
import com.orcalabs.hrms.data.mock.MockEmployeeData
import com.orcalabs.hrms.data.mock.MockFieldDutyData
import com.orcalabs.hrms.data.mock.MockLeaveData
import com.orcalabs.hrms.data.mock.MockOfficeData
import com.orcalabs.hrms.data.mock.MockTaskData
import com.orcalabs.hrms.data.model.AttendanceRecord
import com.orcalabs.hrms.data.model.AttendanceStatus
import com.orcalabs.hrms.data.model.ChatChannel
import com.orcalabs.hrms.data.model.ChatMessage
import com.orcalabs.hrms.data.model.DoctorPlanItem
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.ExecutiveOverviewData
import com.orcalabs.hrms.data.model.FieldDutySession
import com.orcalabs.hrms.data.model.FieldVisit
import com.orcalabs.hrms.data.model.GeoPoint
import com.orcalabs.hrms.data.model.HrmsTask
import com.orcalabs.hrms.data.model.LeaveBalanceSummary
import com.orcalabs.hrms.data.model.LeaveRequest
import com.orcalabs.hrms.data.model.LeaveStatus
import com.orcalabs.hrms.data.model.OfficeLocation
import com.orcalabs.hrms.data.model.PunchType
import com.orcalabs.hrms.data.model.TaskStatus
import com.orcalabs.hrms.data.model.TodayPunchState
import com.orcalabs.hrms.data.model.ZoneMetric
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.ChatRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import com.orcalabs.hrms.domain.repository.OfficeRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepository @Inject constructor() : AuthRepository {
    // Default to Sandeep Reddy (BE) on startup so the app is immediately usable
    private val _currentUser = MutableStateFlow<Employee?>(MockEmployeeData.defaultFieldUser)
    override val currentUser: StateFlow<Employee?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<Employee> {
        delay(600) // Simulate fast network round-trip
        val found = MockEmployeeData.findByEmail(email)
        return if (found != null) {
            // In the existing Pharma DB, passwords match the email or any non-blank value for testing
            _currentUser.value = found
            Result.success(found)
        } else {
            Result.failure(Exception("Employee not found for $email. Try sandeep.reddy@orcalabs.in or divya.sirisha@orcalabs.in"))
        }
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    override suspend fun switchUserRoleForTesting(employeeId: String) {
        val emp = MockEmployeeData.findById(employeeId)
        if (emp != null) {
            _currentUser.value = emp
        }
    }
}

@Singleton
class MockAttendanceRepository @Inject constructor() : AttendanceRepository {
    private val records = MutableStateFlow(MockAttendanceData.initialRecords)
    private val _todayState = MutableStateFlow(
        TodayPunchState(
            isPunchedIn = true,
            punchInTime = "09:30 AM",
            punchOutTime = null,
            activeDurationFormatted = "06h 15m",
            todayStatus = AttendanceStatus.PRESENT,
            locationVerified = true,
            currentLocation = GeoPoint(17.4156, 78.4750, 5.0f, "Banjara Hills Rd 12, Hyderabad")
        )
    )
    override val todayState: StateFlow<TodayPunchState> = _todayState.asStateFlow()

    override fun getAttendanceHistory(employeeId: String): Flow<List<AttendanceRecord>> = flow {
        records.collect { list ->
            emit(list.filter { it.employeeId == employeeId })
        }
    }

    override fun getAllAttendanceForDate(date: String): Flow<List<AttendanceRecord>> = flow {
        records.collect { list ->
            emit(list.filter { it.date == date })
        }
    }

    override suspend fun punch(type: PunchType, location: GeoPoint, photoUri: String?): Result<AttendanceRecord> {
        delay(800)
        val nowTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val nowDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (type == PunchType.IN) {
            _todayState.value = TodayPunchState(
                isPunchedIn = true,
                punchInTime = nowTime,
                todayStatus = AttendanceStatus.PRESENT,
                locationVerified = true,
                currentLocation = location
            )
            val newRecord = AttendanceRecord(
                id = "ATT-${UUID.randomUUID().toString().take(8)}",
                employeeId = "OL010",
                employeeName = "Sandeep Reddy G",
                date = nowDate,
                punchInTime = nowTime,
                punchOutTime = null,
                status = AttendanceStatus.PRESENT,
                punchInLocation = location,
                punchInPhotoUri = photoUri,
                isGeofenceVerified = true
            )
            records.value = (listOf(newRecord) + records.value).toMutableList()
            return Result.success(newRecord)
        } else {
            val updatedState = _todayState.value.copy(
                isPunchedIn = false,
                punchOutTime = nowTime
            )
            _todayState.value = updatedState
            val updatedList = records.value.map {
                if (it.date == nowDate && it.employeeId == "OL010") {
                    it.copy(punchOutTime = nowTime, punchOutLocation = location)
                } else it
            }
            records.value = updatedList.toMutableList()
            return Result.success(updatedList.first())
        }
    }
}

@Singleton
class MockLeaveRepository @Inject constructor() : LeaveRepository {
    private val requests = MutableStateFlow(MockLeaveData.initialLeaveRequests)
    private val balances = MutableStateFlow(MockLeaveData.defaultBalances)

    override fun getMyLeaveRequests(employeeId: String): Flow<List<LeaveRequest>> = flow {
        requests.collect { list ->
            emit(list.filter { it.employeeId == employeeId })
        }
    }

    override fun getAllPendingRequests(): Flow<List<LeaveRequest>> = flow {
        requests.collect { list ->
            emit(list.filter { it.status == LeaveStatus.PENDING })
        }
    }

    override fun getLeaveBalance(employeeId: String): Flow<LeaveBalanceSummary> = flow {
        balances.collect { map ->
            emit(map[employeeId] ?: LeaveBalanceSummary())
        }
    }

    override suspend fun applyLeave(request: LeaveRequest): Result<LeaveRequest> {
        delay(600)
        requests.value = (listOf(request) + requests.value).toMutableList()
        return Result.success(request)
    }

    override suspend fun updateLeaveStatus(
        requestId: String,
        status: LeaveStatus,
        reviewerName: String,
        remarks: String
    ): Result<Unit> {
        delay(500)
        val now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        requests.value = requests.value.map {
            if (it.id == requestId) {
                it.copy(status = status, reviewedBy = reviewerName, reviewRemarks = remarks, reviewedOn = now)
            } else it
        }.toMutableList()
        return Result.success(Unit)
    }
}

@Singleton
class MockFieldDutyRepository @Inject constructor() : FieldDutyRepository {
    private val _activeSession = MutableStateFlow(MockFieldDutyData.currentSession)
    override val activeSession: StateFlow<FieldDutySession?> = _activeSession.asStateFlow()

    private val _plannedDoctors = MutableStateFlow<List<DoctorPlanItem>>(MockFieldDutyData.plannedDoctors)
    override val plannedDoctors: StateFlow<List<DoctorPlanItem>> = _plannedDoctors.asStateFlow()

    private val _visitsToday = MutableStateFlow<List<FieldVisit>>(MockFieldDutyData.completedVisitsToday)
    override val visitsToday: StateFlow<List<FieldVisit>> = _visitsToday.asStateFlow()

    override suspend fun startFieldSession(startLocation: GeoPoint): Result<FieldDutySession> {
        delay(500)
        val now = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val session = FieldDutySession(
            id = "SESSION-${UUID.randomUUID().toString().take(8)}",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            startTime = now,
            isSessionActive = true,
            totalVisitsPlanned = _plannedDoctors.value.size,
            visitsCompleted = 0,
            startLocation = startLocation
        )
        _activeSession.value = session
        return Result.success(session)
    }

    override suspend fun endFieldSession(endLocation: GeoPoint): Result<Unit> {
        delay(500)
        val now = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        _activeSession.value = _activeSession.value?.copy(
            isSessionActive = false,
            endTime = now,
            endLocation = endLocation
        )
        return Result.success(Unit)
    }

    override suspend fun recordVisit(visit: FieldVisit): Result<FieldVisit> {
        delay(600)
        _visitsToday.value = (listOf(visit) + _visitsToday.value).toMutableList()
        _activeSession.value = _activeSession.value?.copy(
            visitsCompleted = _visitsToday.value.size,
            totalPobBooked = _activeSession.value!!.totalPobBooked + visit.pobValueInr
        )
        return Result.success(visit)
    }

    override suspend fun markDoctorVisited(doctorId: String) {
        _plannedDoctors.value = _plannedDoctors.value.map {
            if (it.doctorId == doctorId) it.copy(isVisitedToday = true) else it
        }
    }
}

@Singleton
class MockTaskRepository @Inject constructor() : TaskRepository {
    private val tasks = MutableStateFlow(MockTaskData.initialTasks)

    override fun getTasksForUser(employeeId: String): Flow<List<HrmsTask>> = flow {
        tasks.collect { list ->
            emit(list.filter { it.assignedToId == employeeId })
        }
    }

    override fun getAllTasks(): Flow<List<HrmsTask>> = flow {
        tasks.collect { emit(it) }
    }

    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus, notes: String?): Result<Unit> {
        delay(400)
        val now = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault()).format(Date())
        tasks.value = tasks.value.map {
            if (it.id == taskId) {
                it.copy(
                    status = status,
                    completedAt = if (status == TaskStatus.COMPLETED) now else it.completedAt,
                    submissionNotes = notes ?: it.submissionNotes
                )
            } else it
        }.toMutableList()
        return Result.success(Unit)
    }

    override suspend fun createTask(task: HrmsTask): Result<HrmsTask> {
        delay(500)
        tasks.value = (listOf(task) + tasks.value).toMutableList()
        return Result.success(task)
    }
}

@Singleton
class MockChatRepository @Inject constructor() : ChatRepository {
    private val _channels = MutableStateFlow(MockChatData.channels)
    override val channels: StateFlow<List<ChatChannel>> = _channels.asStateFlow()

    private val messages = MutableStateFlow(MockChatData.initialMessages)

    override fun getMessages(channelId: String): Flow<List<ChatMessage>> = flow {
        messages.collect { list ->
            emit(list.filter { it.channelId == channelId })
        }
    }

    override suspend fun sendMessage(channelId: String, text: String): Result<ChatMessage> {
        delay(300)
        val now = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newMsg = ChatMessage(
            id = "M-${UUID.randomUUID().toString().take(6)}",
            senderId = "OL010",
            senderName = "Sandeep Reddy G",
            senderRole = "BE Banjara Hills",
            text = text,
            timestamp = now,
            isFromMe = true,
            channelId = channelId
        )
        messages.value = (messages.value + newMsg).toMutableList()
        _channels.value = _channels.value.map {
            if (it.id == channelId) it.copy(lastMessage = text, lastMessageTime = now) else it
        }
        return Result.success(newMsg)
    }
}

@Singleton
class MockEmployeeRepository @Inject constructor() : EmployeeRepository {
    private val employees = MutableStateFlow(MockEmployeeData.employees)

    override fun getAllEmployees(): Flow<List<Employee>> = flow {
        employees.collect { emit(it) }
    }

    override fun getEmployeeById(id: String): Flow<Employee?> = flow {
        employees.collect { list ->
            emit(list.firstOrNull { it.id == id })
        }
    }

    override fun getExecutiveOverview(): Flow<ExecutiveOverviewData> = flow {
        emit(
            ExecutiveOverviewData(
                totalHeadcount = MockEmployeeData.employees.size,
                presentToday = 28,
                onFieldToday = 21,
                onLeaveToday = 3,
                absentToday = 4,
                totalDoctorCallsToday = 164,
                totalPobTodayInr = 348500.0,
                monthlyPobTargetAchievedPct = 84.5,
                zones = listOf(
                    ZoneMetric("Telangana (Hyderabad, Warangal, Karimnagar)", 14, 12, 10, 88, 192000.0, 220000.0),
                    ZoneMetric("Coastal Andhra (Vizag, Vijayawada, Guntur)", 12, 10, 8, 54, 114500.0, 140000.0),
                    ZoneMetric("Rayalaseema (Tirupati, Kurnool, Nellore)", 9, 6, 3, 22, 42000.0, 60000.0)
                )
            )
        )
    }
}

@Singleton
class MockOfficeRepository @Inject constructor() : OfficeRepository {
    private val offices = MutableStateFlow(MockOfficeData.offices)

    override fun getAllOffices(): Flow<List<OfficeLocation>> = flow {
        offices.collect { emit(it) }
    }

    override suspend fun addOffice(office: OfficeLocation): Result<OfficeLocation> {
        delay(400)
        offices.value = (offices.value + office).toMutableList()
        return Result.success(office)
    }
}
