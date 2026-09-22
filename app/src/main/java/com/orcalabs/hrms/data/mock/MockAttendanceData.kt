package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.AttendanceRecord
import com.orcalabs.hrms.data.model.AttendanceStatus
import com.orcalabs.hrms.data.model.GeoPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object MockAttendanceData {

    private val hyderabadHqGeo = GeoPoint(17.4399, 78.3844, 4.2f, "Orca Labs Corporate, HITEC City, Hyderabad")

    val initialRecords: MutableList<AttendanceRecord> = mutableListOf(
        AttendanceRecord(
            id = "ATT-20260922-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-22",
            punchInTime = "09:30 AM",
            punchOutTime = null,
            totalHours = 6.2,
            status = AttendanceStatus.PRESENT,
            punchInLocation = GeoPoint(17.4156, 78.4750, 6.0f, "Banjara Hills Rd 12, Hyderabad"),
            isGeofenceVerified = true
        ),
        AttendanceRecord(
            id = "ATT-20260921-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-21",
            punchInTime = "09:15 AM",
            punchOutTime = "06:45 PM",
            totalHours = 9.5,
            status = AttendanceStatus.PRESENT,
            punchInLocation = GeoPoint(17.4150, 78.4740, 5.0f, "Care Hospital, Banjara Hills"),
            punchOutLocation = GeoPoint(17.4160, 78.4760, 5.0f, "Apollo Hospitals, Jubilee Hills"),
            isGeofenceVerified = true
        ),
        AttendanceRecord(
            id = "ATT-20260920-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-20",
            punchInTime = null,
            punchOutTime = null,
            totalHours = 0.0,
            status = AttendanceStatus.WEEK_OFF,
            remarks = "Sunday Regular Weekly Off"
        ),
        AttendanceRecord(
            id = "ATT-20260919-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-19",
            punchInTime = "09:20 AM",
            punchOutTime = "06:15 PM",
            totalHours = 8.9,
            status = AttendanceStatus.PRESENT,
            punchInLocation = GeoPoint(17.4399, 78.3844, 4.0f, "Orca Labs Corporate Office"),
            isGeofenceVerified = true
        ),
        AttendanceRecord(
            id = "ATT-20260918-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-18",
            punchInTime = "09:05 AM",
            punchOutTime = "01:30 PM",
            totalHours = 4.4,
            status = AttendanceStatus.HALF_DAY,
            punchInLocation = GeoPoint(17.4399, 78.3844, 5.0f, "Orca Labs Corporate Office"),
            remarks = "Approved half-day personal appointment"
        ),
        AttendanceRecord(
            id = "ATT-20260917-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-17",
            punchInTime = "08:58 AM",
            punchOutTime = "06:40 PM",
            totalHours = 9.7,
            status = AttendanceStatus.FIELD_DUTY,
            punchInLocation = GeoPoint(17.4485, 78.3908, 6.0f, "Madhapur Clinics"),
            isGeofenceVerified = true
        ),
        AttendanceRecord(
            id = "ATT-20260916-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-16",
            punchInTime = "09:10 AM",
            punchOutTime = "06:20 PM",
            totalHours = 9.1,
            status = AttendanceStatus.PRESENT,
            punchInLocation = GeoPoint(17.4399, 78.3844, 4.0f, "Orca Labs Corporate Office"),
            isGeofenceVerified = true
        ),
        AttendanceRecord(
            id = "ATT-20260915-010",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            date = "2026-09-15",
            punchInTime = null,
            punchOutTime = null,
            totalHours = 0.0,
            status = AttendanceStatus.ON_LEAVE,
            remarks = "Casual Leave approved"
        )
    )

    fun getRecordsForEmployee(employeeId: String): List<AttendanceRecord> {
        return initialRecords.filter { it.employeeId == employeeId }
    }

    fun getAllRecordsForDate(date: String): List<AttendanceRecord> {
        return initialRecords.filter { it.date == date }
    }
}
