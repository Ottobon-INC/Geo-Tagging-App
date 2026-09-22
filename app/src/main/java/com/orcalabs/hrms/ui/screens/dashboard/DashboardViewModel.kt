package com.orcalabs.hrms.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.DoctorPlanItem
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.FieldDutySession
import com.orcalabs.hrms.data.model.FieldVisit
import com.orcalabs.hrms.data.model.HrmsTask
import com.orcalabs.hrms.data.model.LeaveBalanceSummary
import com.orcalabs.hrms.data.model.TodayPunchState
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val currentUser: Employee? = null,
    val punchState: TodayPunchState = TodayPunchState(),
    val leaveBalance: LeaveBalanceSummary = LeaveBalanceSummary(),
    val activeFieldSession: FieldDutySession? = null,
    val completedVisitsToday: List<FieldVisit> = emptyList(),
    val plannedDoctors: List<DoctorPlanItem> = emptyList(),
    val pendingTasks: List<HrmsTask> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository,
    private val leaveRepository: LeaveRepository,
    private val fieldDutyRepository: FieldDutyRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                _uiState.value = _uiState.value.copy(currentUser = user)
                if (user != null) {
                    loadUserData(user.id)
                }
            }
        }

        viewModelScope.launch {
            attendanceRepository.todayState.collect { state ->
                _uiState.value = _uiState.value.copy(punchState = state)
            }
        }

        viewModelScope.launch {
            fieldDutyRepository.activeSession.collect { session ->
                _uiState.value = _uiState.value.copy(activeFieldSession = session)
            }
        }

        viewModelScope.launch {
            fieldDutyRepository.visitsToday.collect { visits ->
                _uiState.value = _uiState.value.copy(completedVisitsToday = visits)
            }
        }

        viewModelScope.launch {
            fieldDutyRepository.plannedDoctors.collect { docs ->
                _uiState.value = _uiState.value.copy(plannedDoctors = docs)
            }
        }
    }

    private fun loadUserData(employeeId: String) {
        viewModelScope.launch {
            leaveRepository.getLeaveBalance(employeeId).collect { bal ->
                _uiState.value = _uiState.value.copy(leaveBalance = bal)
            }
        }
        viewModelScope.launch {
            taskRepository.getTasksForUser(employeeId).collect { tasks ->
                _uiState.value = _uiState.value.copy(pendingTasks = tasks)
            }
        }
    }
}
