package com.orcalabs.hrms.data.model

enum class PunchType {
    IN, OUT
}

enum class AttendanceStatus(val label: String) {
    PRESENT("Present"),
    ABSENT("Absent"),
    HALF_DAY("Half Day"),
    ON_LEAVE("On Leave"),
    WEEK_OFF("Week Off"),
    HOLIDAY("Holiday"),
    FIELD_DUTY("Field Duty")
}

data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float = 5.0f,
    val address: String = "Orca Labs HQ, Hyderabad"
)

data class AttendanceRecord(
    val id: String,
    val employeeId: String,
    val employeeName: String,
    val date: String,                  // "YYYY-MM-DD"
    val punchInTime: String?,          // "09:15 AM"
    val punchOutTime: String?,         // "06:30 PM"
    val totalHours: Double = 8.5,
    val status: AttendanceStatus,
    val punchInLocation: GeoPoint? = null,
    val punchOutLocation: GeoPoint? = null,
    val punchInPhotoUri: String? = null,
    val isGeofenceVerified: Boolean = true,
    val remarks: String? = null
)

data class TodayPunchState(
    val isPunchedIn: Boolean = false,
    val punchInTime: String? = null,
    val punchOutTime: String? = null,
    val activeDurationFormatted: String = "00:00:00",
    val todayStatus: AttendanceStatus = AttendanceStatus.ABSENT,
    val locationVerified: Boolean = false,
    val currentLocation: GeoPoint? = null
)
