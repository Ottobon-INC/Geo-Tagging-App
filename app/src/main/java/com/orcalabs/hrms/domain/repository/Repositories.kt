package com.orcalabs.hrms.domain.repository

import com.orcalabs.hrms.data.model.AttendanceRecord
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<Employee?>
    suspend fun login(email: String, password: String): Result<Employee>
    suspend fun logout()
    suspend fun switchUserRoleForTesting(employeeId: String)
}

interface AttendanceRepository {
    val todayState: StateFlow<TodayPunchState>
    fun getAttendanceHistory(employeeId: String): Flow<List<AttendanceRecord>>
    fun getAllAttendanceForDate(date: String): Flow<List<AttendanceRecord>>
    suspend fun punch(type: PunchType, location: GeoPoint, photoUri: String? = null): Result<AttendanceRecord>
}

interface LeaveRepository {
    fun getMyLeaveRequests(employeeId: String): Flow<List<LeaveRequest>>
    fun getAllPendingRequests(): Flow<List<LeaveRequest>>
    fun getLeaveBalance(employeeId: String): Flow<LeaveBalanceSummary>
    suspend fun applyLeave(request: LeaveRequest): Result<LeaveRequest>
    suspend fun updateLeaveStatus(requestId: String, status: LeaveStatus, reviewerName: String, remarks: String): Result<Unit>
}

interface FieldDutyRepository {
    val activeSession: StateFlow<FieldDutySession?>
    val plannedDoctors: StateFlow<List<DoctorPlanItem>>
    val visitsToday: StateFlow<List<FieldVisit>>
    suspend fun startFieldSession(startLocation: GeoPoint): Result<FieldDutySession>
    suspend fun endFieldSession(endLocation: GeoPoint): Result<Unit>
    suspend fun recordVisit(visit: FieldVisit): Result<FieldVisit>
    suspend fun markDoctorVisited(doctorId: String)
}

interface TaskRepository {
    fun getTasksForUser(employeeId: String): Flow<List<HrmsTask>>
    fun getAllTasks(): Flow<List<HrmsTask>>
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus, notes: String? = null): Result<Unit>
    suspend fun createTask(task: HrmsTask): Result<HrmsTask>
}

interface ChatRepository {
    val channels: StateFlow<List<ChatChannel>>
    fun getMessages(channelId: String): Flow<List<ChatMessage>>
    suspend fun sendMessage(channelId: String, text: String): Result<ChatMessage>
}

interface EmployeeRepository {
    fun getAllEmployees(): Flow<List<Employee>>
    fun getEmployeeById(id: String): Flow<Employee?>
    fun getExecutiveOverview(): Flow<ExecutiveOverviewData>
}

interface OfficeRepository {
    fun getAllOffices(): Flow<List<OfficeLocation>>
    suspend fun addOffice(office: OfficeLocation): Result<OfficeLocation>
}
