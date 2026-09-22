package com.orcalabs.hrms.ui.screens.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.AttendanceRecord
import com.orcalabs.hrms.data.model.AttendanceStatus
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AttendanceHistoryUiState(
    val records: List<AttendanceRecord> = emptyList(),
    val filteredRecords: List<AttendanceRecord> = emptyList(),
    val selectedFilter: String = "ALL", // "ALL", "PRESENT", "FIELD_DUTY", "LEAVE"
    val totalPresent: Int = 0,
    val totalFieldDuty: Int = 0,
    val totalHalfDay: Int = 0,
    val totalLeaves: Int = 0
)

@HiltViewModel
class AttendanceHistoryViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AttendanceHistoryUiState())
    val uiState: StateFlow<AttendanceHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                val empId = user?.id ?: "OL010"
                loadHistory(empId)
            }
        }
    }

    private fun loadHistory(empId: String) {
        viewModelScope.launch {
            attendanceRepository.getAttendanceHistory(empId).collect { list ->
                val present = list.count { it.status == AttendanceStatus.PRESENT }
                val field = list.count { it.status == AttendanceStatus.FIELD_DUTY }
                val half = list.count { it.status == AttendanceStatus.HALF_DAY }
                val leave = list.count { it.status == AttendanceStatus.ON_LEAVE }

                _uiState.value = _uiState.value.copy(
                    records = list,
                    filteredRecords = applyFilter(list, _uiState.value.selectedFilter),
                    totalPresent = present,
                    totalFieldDuty = field,
                    totalHalfDay = half,
                    totalLeaves = leave
                )
            }
        }
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter,
            filteredRecords = applyFilter(_uiState.value.records, filter)
        )
    }

    private fun applyFilter(records: List<AttendanceRecord>, filter: String): List<AttendanceRecord> {
        return when (filter) {
            "PRESENT" -> records.filter { it.status == AttendanceStatus.PRESENT }
            "FIELD_DUTY" -> records.filter { it.status == AttendanceStatus.FIELD_DUTY }
            "LEAVE" -> records.filter { it.status == AttendanceStatus.ON_LEAVE || it.status == AttendanceStatus.HALF_DAY }
            else -> records
        }
    }
}
