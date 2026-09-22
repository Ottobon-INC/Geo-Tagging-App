package com.orcalabs.hrms.ui.screens.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.GeoPoint
import com.orcalabs.hrms.data.model.PunchType
import com.orcalabs.hrms.data.model.TodayPunchState
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class PunchUiState(
    val currentTimeString: String = "",
    val currentDateString: String = "",
    val punchState: TodayPunchState = TodayPunchState(),
    val isGeofenceValid: Boolean = true,
    val distanceMeters: Int = 28,
    val officeName: String = "Orca Labs Corporate HQ, Hyderabad",
    val isSelfieCaptured: Boolean = true,
    val selfiePreviewUrl: String? = null,
    val isWorkingInField: Boolean = false,
    val isPunching: Boolean = false,
    val punchSuccessMessage: String? = null
)

@HiltViewModel
class PunchViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PunchUiState())
    val uiState: StateFlow<PunchUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            attendanceRepository.todayState.collect { state ->
                _uiState.value = _uiState.value.copy(punchState = state)
            }
        }

        // Live Clock ticker
        viewModelScope.launch {
            val timeFmt = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
            val dateFmt = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            while (true) {
                val now = Date()
                _uiState.value = _uiState.value.copy(
                    currentTimeString = timeFmt.format(now),
                    currentDateString = dateFmt.format(now)
                )
                delay(1000)
            }
        }
    }

    fun toggleFieldMode(isField: Boolean) {
        _uiState.value = _uiState.value.copy(isWorkingInField = isField)
    }

    fun captureSelfie() {
        _uiState.value = _uiState.value.copy(
            isSelfieCaptured = true,
            selfiePreviewUrl = "mock_selfie_captured"
        )
    }

    fun executePunch() {
        val currentlyPunchedIn = _uiState.value.punchState.isPunchedIn
        val punchType = if (currentlyPunchedIn) PunchType.OUT else PunchType.IN

        _uiState.value = _uiState.value.copy(isPunching = true, punchSuccessMessage = null)
        viewModelScope.launch {
            val location = GeoPoint(17.4435, 78.3772, 4.0f, _uiState.value.officeName)
            val result = attendanceRepository.punch(punchType, location, _uiState.value.selfiePreviewUrl)
            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isPunching = false,
                    punchSuccessMessage = if (punchType == PunchType.IN)
                        "Punch In Successful at ${_uiState.value.currentTimeString}"
                    else "Punch Out Successful at ${_uiState.value.currentTimeString}"
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(isPunching = false)
            }
        }
    }
}
