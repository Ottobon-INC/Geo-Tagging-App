package com.orcalabs.hrms.data.model

data class ZoneMetric(
    val zoneName: String,
    val totalEmployees: Int,
    val presentToday: Int,
    val activeOnField: Int,
    val totalCallsToday: Int,
    val totalPobAmountInr: Double,
    val targetPobInr: Double
)

data class ExecutiveOverviewData(
    val totalHeadcount: Int = 35,
    val presentToday: Int = 28,
    val onFieldToday: Int = 21,
    val onLeaveToday: Int = 3,
    val absentToday: Int = 4,
    val totalDoctorCallsToday: Int = 164,
    val totalPobTodayInr: Double = 348500.0,
    val monthlyPobTargetAchievedPct: Double = 84.5,
    val zones: List<ZoneMetric> = emptyList()
)
