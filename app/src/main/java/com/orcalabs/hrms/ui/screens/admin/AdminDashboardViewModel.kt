package com.orcalabs.hrms.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.ExecutiveOverviewData
import com.orcalabs.hrms.data.model.LeaveRequest
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminDashboardUiState(
    val currentUser: Employee? = null,
    val executiveData: ExecutiveOverviewData = ExecutiveOverviewData(),
    val pendingLeavesCount: Int = 0,
    val totalEmployeesCount: Int = 35
)

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val employeeRepository: EmployeeRepository,
    private val leaveRepository: LeaveRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminDashboardUiState())
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                _uiState.value = _uiState.value.copy(currentUser = user)
            }
        }
        viewModelScope.launch {
            employeeRepository.getExecutiveOverview().collect { data ->
                _uiState.value = _uiState.value.copy(executiveData = data)
            }
        }
        viewModelScope.launch {
            leaveRepository.getAllPendingRequests().collect { list ->
                _uiState.value = _uiState.value.copy(pendingLeavesCount = list.size)
            }
        }
    }
}
