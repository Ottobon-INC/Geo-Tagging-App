package com.orcalabs.hrms.data.model

data class OfficeLocation(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Double = 150.0,
    val isActive: Boolean = true,
    val totalEmployeesAssigned: Int = 18
)
