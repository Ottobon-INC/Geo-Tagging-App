package com.orcalabs.hrms.data.model

enum class UserRole(val displayName: String, val level: Int) {
    BE("Business Executive (Field)", 1),
    RSM("Regional Sales Manager", 2),
    ZSM("Zonal Sales Manager", 3),
    GM("General Manager", 4),
    HR("HR Manager", 4),
    ADMIN("Administrator", 5);

    val isFieldForce: Boolean get() = this == BE
    val isManager: Boolean get() = this == RSM || this == ZSM
    val isAdminOrExecutive: Boolean get() = this == GM || this == HR || this == ADMIN
}

data class Employee(
    val id: String,                    // e.g. "OL009", "OL003", "OL030"
    val fullName: String,
    val email: String,
    val phone: String,
    val role: UserRole,
    val designation: String,
    val department: String,            // "Sales & Marketing", "HR", "Operations", "Executive"
    val zone: String? = null,          // "Telangana", "Andhra Pradesh", "Corporate"
    val headquarter: String? = null,   // "Hyderabad", "Vijayawada", "Warangal", etc.
    val reportsToId: String? = null,
    val reportsToName: String? = null,
    val joiningDate: String = "2023-01-15",
    val avatarUrl: String? = null,
    val isActive: Boolean = true,
    val leavesBalance: Int = 18,
    val isPunchInToday: Boolean = false,
    val lastPunchTime: String? = null
)
