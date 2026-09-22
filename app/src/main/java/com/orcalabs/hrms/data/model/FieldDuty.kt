package com.orcalabs.hrms.data.model

enum class VisitType(val label: String) {
    DOCTOR("Doctor Call"),
    CHEMIST("Chemist / Pharmacy"),
    STOCKIST("Stockist / Distributor"),
    HOSPITAL("Hospital / Institute")
}

data class ProductPromotion(
    val productId: String,
    val productName: String,           // e.g. "Amoxi-Orca 500mg", "Orca-Cef 200", "Paracetamol 650"
    val isDetailed: Boolean = true,
    val samplesGiven: Int = 0,
    val promoGiftsGiven: Int = 0
)

data class FieldVisit(
    val id: String,
    val employeeId: String,
    val employeeName: String,
    val visitType: VisitType,
    val contactPerson: String,         // e.g. "Dr. K. Srinivas Rao, MD (Cardio)"
    val clinicOrShopName: String,      // e.g. "Apollo Clinics, Jubilee Hills"
    val location: GeoPoint,
    val timestamp: String = "11:30 AM",
    val date: String = "2026-09-22",
    val productsPromoted: List<ProductPromotion> = emptyList(),
    val pobValueInr: Double = 0.0,     // Personal Order Booking in Rupees
    val doctorFeedback: String = "Prescribes regularly. Discussed new clinical trial results.",
    val nextVisitDate: String = "2026-10-05",
    val isJointWorking: Boolean = false,
    val accompaniedBy: String? = null  // e.g. "B V Janardhan (ZSM)"
)

data class FieldDutySession(
    val id: String,
    val employeeId: String,
    val employeeName: String,
    val date: String,
    val startTime: String,
    val endTime: String? = null,
    val isSessionActive: Boolean = true,
    val totalVisitsPlanned: Int = 10,
    val visitsCompleted: Int = 0,
    val totalPobBooked: Double = 0.0,
    val startLocation: GeoPoint? = null,
    val endLocation: GeoPoint? = null
)

data class DoctorPlanItem(
    val doctorId: String,
    val doctorName: String,
    val specialty: String,
    val hospitalName: String,
    val area: String,
    val priority: String = "A+",
    val isVisitedToday: Boolean = false
)
