package com.orcalabs.hrms.data.model

enum class LeaveType(val displayName: String) {
    CASUAL("Casual Leave"),
    SICK("Sick / Medical Leave"),
    EARNED("Earned Leave"),
    COMP_OFF("Compensatory Off"),
    MATERNITY_PATERNITY("Parental Leave")
}

enum class LeaveStatus(val label: String) {
    PENDING("Pending Approval"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled")
}

data class LeaveRequest(
    val id: String,
    val employeeId: String,
    val employeeName: String,
    val leaveType: LeaveType,
    val startDate: String,             // "2026-09-25"
    val endDate: String,               // "2026-09-26"
    val numberOfDays: Double = 1.0,
    val reason: String,
    val status: LeaveStatus = LeaveStatus.PENDING,
    val appliedOn: String = "2026-09-22",
    val reviewedBy: String? = null,
    val reviewRemarks: String? = null,
    val reviewedOn: String? = null
)

data class LeaveBalanceSummary(
    val casualTotal: Int = 12,
    val casualUsed: Int = 3,
    val sickTotal: Int = 10,
    val sickUsed: Int = 2,
    val earnedTotal: Int = 15,
    val earnedUsed: Int = 4
) {
    val totalRemaining: Int
        get() = (casualTotal - casualUsed) + (sickTotal - sickUsed) + (earnedTotal - earnedUsed)
}
