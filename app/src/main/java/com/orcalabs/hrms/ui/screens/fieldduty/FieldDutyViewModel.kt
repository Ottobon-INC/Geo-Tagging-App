package com.orcalabs.hrms.ui.screens.fieldduty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.mock.MockFieldDutyData
import com.orcalabs.hrms.data.model.DoctorPlanItem
import com.orcalabs.hrms.data.model.FieldDutySession
import com.orcalabs.hrms.data.model.FieldVisit
import com.orcalabs.hrms.data.model.GeoPoint
import com.orcalabs.hrms.data.model.ProductPromotion
import com.orcalabs.hrms.data.model.VisitType
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
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

data class CallCaptureFormState(
    val visitType: VisitType = VisitType.DOCTOR,
    val contactPerson: String = "Dr. K. Srinivas Rao, MD",
    val clinicOrShopName: String = "Apollo Clinics, Banjara Hills",
    val selectedProducts: Set<String> = setOf("Orca-Cef 200 (Cefixime 200mg)", "Pan-Orca DSR (Pantoprazole + Domperidone)"),
    val sampleQuantities: Map<String, Int> = mapOf("Orca-Cef 200 (Cefixime 200mg)" to 3),
    val pobValue: String = "0",
    val feedbackNotes: String = "Discussed high clinical efficacy. Agreed to prescribe.",
    val nextVisitDate: String = "2026-10-06",
    val isJointWorking: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)

data class FieldDutyUiState(
    val activeSession: FieldDutySession? = null,
    val visitsToday: List<FieldVisit> = emptyList(),
    val plannedDoctors: List<DoctorPlanItem> = emptyList(),
    val productsList: List<String> = MockFieldDutyData.pharmaProducts,
    val callForm: CallCaptureFormState = CallCaptureFormState()
)

@HiltViewModel
class FieldDutyViewModel @Inject constructor(
    private val fieldDutyRepository: FieldDutyRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FieldDutyUiState())
    val uiState: StateFlow<FieldDutyUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fieldDutyRepository.activeSession.collect { session ->
                _uiState.value = _uiState.value.copy(activeSession = session)
            }
        }
        viewModelScope.launch {
            fieldDutyRepository.visitsToday.collect { visits ->
                _uiState.value = _uiState.value.copy(visitsToday = visits)
            }
        }
        viewModelScope.launch {
            fieldDutyRepository.plannedDoctors.collect { docs ->
                _uiState.value = _uiState.value.copy(plannedDoctors = docs)
            }
        }
    }

    fun startSession() {
        viewModelScope.launch {
            val loc = GeoPoint(17.4156, 78.4750, 4.0f, "Banjara Hills Rd 12, Hyderabad")
            fieldDutyRepository.startFieldSession(loc)
        }
    }

    fun endSession() {
        viewModelScope.launch {
            val loc = GeoPoint(17.4156, 78.4750, 4.0f, "Banjara Hills Rd 12, Hyderabad")
            fieldDutyRepository.endFieldSession(loc)
        }
    }

    fun updateCallType(type: VisitType) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(visitType = type)
        )
    }

    fun updateContactPerson(name: String) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(contactPerson = name)
        )
    }

    fun updateClinicName(name: String) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(clinicOrShopName = name)
        )
    }

    fun toggleProduct(product: String) {
        val current = _uiState.value.callForm.selectedProducts.toMutableSet()
        if (current.contains(product)) current.remove(product) else current.add(product)
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(selectedProducts = current)
        )
    }

    fun updateSampleQty(product: String, delta: Int) {
        val current = _uiState.value.callForm.sampleQuantities.toMutableMap()
        val existing = current[product] ?: 0
        val updated = (existing + delta).coerceAtLeast(0)
        current[product] = updated
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(sampleQuantities = current)
        )
    }

    fun updatePobValue(pob: String) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(pobValue = pob)
        )
    }

    fun updateFeedbackNotes(notes: String) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(feedbackNotes = notes)
        )
    }

    fun selectDoctorFromPlanner(doctor: DoctorPlanItem) {
        _uiState.value = _uiState.value.copy(
            callForm = _uiState.value.callForm.copy(
                visitType = VisitType.DOCTOR,
                contactPerson = doctor.doctorName,
                clinicOrShopName = "${doctor.hospitalName}, ${doctor.area}"
            )
        )
    }

    fun saveCall(onSuccess: () -> Unit) {
        val form = _uiState.value.callForm
        val user = authRepository.currentUser.value
        val empId = user?.id ?: "OL010"
        val empName = user?.fullName ?: "Sandeep Reddy G"

        _uiState.value = _uiState.value.copy(callForm = form.copy(isSaving = true))

        viewModelScope.launch {
            val promos = form.selectedProducts.map { prod ->
                ProductPromotion(
                    productId = prod.take(8),
                    productName = prod,
                    isDetailed = true,
                    samplesGiven = form.sampleQuantities[prod] ?: 0
                )
            }

            val visit = FieldVisit(
                id = "VISIT-${UUID.randomUUID().toString().take(6)}",
                employeeId = empId,
                employeeName = empName,
                visitType = form.visitType,
                contactPerson = form.contactPerson,
                clinicOrShopName = form.clinicOrShopName,
                location = GeoPoint(17.4156, 78.4750, 4.0f, form.clinicOrShopName),
                timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
                productsPromoted = promos,
                pobValueInr = form.pobValue.toDoubleOrNull() ?: 0.0,
                doctorFeedback = form.feedbackNotes,
                nextVisitDate = form.nextVisitDate
            )

            fieldDutyRepository.recordVisit(visit)
            _uiState.value = _uiState.value.copy(
                callForm = form.copy(isSaving = false, saveSuccess = true)
            )
            onSuccess()
        }
    }
}
