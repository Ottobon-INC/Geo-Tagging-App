package com.orcalabs.hrms.ui.screens.leave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.LeaveBalanceSummary
import com.orcalabs.hrms.data.model.LeaveRequest
import com.orcalabs.hrms.data.model.LeaveStatus
import com.orcalabs.hrms.data.model.LeaveType
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

data class LeaveUiState(
    val myRequests: List<LeaveRequest> = emptyList(),
    val balance: LeaveBalanceSummary = LeaveBalanceSummary(),
    val isApplyDialogVisible: Boolean = false,
    val selectedLeaveType: LeaveType = LeaveType.CASUAL,
    val startDate: String = "2026-09-28",
    val endDate: String = "2026-09-29",
    val numberOfDays: Double = 2.0,
    val reason: String = "",
    val isSubmitting: Boolean = false,
    val submissionMessage: String? = null
)

@HiltViewModel
class LeaveViewModel @Inject constructor(
    private val leaveRepository: LeaveRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LeaveUiState())
    val uiState: StateFlow<LeaveUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                val empId = user?.id ?: "OL010"
                loadData(empId)
            }
        }
    }

    private fun loadData(employeeId: String) {
        viewModelScope.launch {
            leaveRepository.getMyLeaveRequests(employeeId).collect { list ->
                _uiState.value = _uiState.value.copy(myRequests = list)
            }
        }
        viewModelScope.launch {
            leaveRepository.getLeaveBalance(employeeId).collect { bal ->
                _uiState.value = _uiState.value.copy(balance = bal)
            }
        }
    }

    fun openApplyDialog() {
        _uiState.value = _uiState.value.copy(isApplyDialogVisible = true, reason = "", submissionMessage = null)
    }

    fun closeApplyDialog() {
        _uiState.value = _uiState.value.copy(isApplyDialogVisible = false)
    }

    fun onTypeSelected(type: LeaveType) {
        _uiState.value = _uiState.value.copy(selectedLeaveType = type)
    }

    fun onStartDateChange(date: String) {
        _uiState.value = _uiState.value.copy(startDate = date)
    }

    fun onEndDateChange(date: String) {
        _uiState.value = _uiState.value.copy(endDate = date)
    }

    fun onReasonChange(reason: String) {
        _uiState.value = _uiState.value.copy(reason = reason)
    }

    fun submitLeave() {
        if (_uiState.value.reason.isBlank()) return
        val user = authRepository.currentUser.value
        val empId = user?.id ?: "OL010"
        val empName = user?.fullName ?: "Sandeep Reddy G"

        _uiState.value = _uiState.value.copy(isSubmitting = true)
        viewModelScope.launch {
            val req = LeaveRequest(
                id = "LV-${UUID.randomUUID().toString().take(6).uppercase()}",
                employeeId = empId,
                employeeName = empName,
                leaveType = _uiState.value.selectedLeaveType,
                startDate = _uiState.value.startDate,
                endDate = _uiState.value.endDate,
                numberOfDays = _uiState.value.numberOfDays,
                reason = _uiState.value.reason,
                status = LeaveStatus.PENDING,
                appliedOn = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )
            leaveRepository.applyLeave(req)
            _uiState.value = _uiState.value.copy(
                isSubmitting = false,
                isApplyDialogVisible = false,
                submissionMessage = "Leave application submitted successfully for review!"
            )
        }
    }
}
