package com.orcalabs.hrms.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.ExecutiveOverviewData
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExecutiveUiState(
    val data: ExecutiveOverviewData = ExecutiveOverviewData(),
    val doctorCoveragePct: Double = 86.4,
    val chemistCoveragePct: Double = 91.8,
    val avgCallsPerRep: Double = 9.8,
    val topPerformingRep: String = "Sandeep Reddy G (₹4.2L Q3 POB)"
)

@HiltViewModel
class ExecutiveViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExecutiveUiState())
    val uiState: StateFlow<ExecutiveUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            employeeRepository.getExecutiveOverview().collect { overview ->
                _uiState.value = _uiState.value.copy(data = overview)
            }
        }
    }
}
