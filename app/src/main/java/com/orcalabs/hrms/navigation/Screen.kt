package com.orcalabs.hrms.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector = Icons.Default.Home
) {
    // Auth
    object Login : Screen("login", "Login")

    // Employee & Field Screens
    object Dashboard : Screen("dashboard", "Home", Icons.Default.Home)
    object Punch : Screen("punch", "Punch Attendance", Icons.Default.Fingerprint)
    object AttendanceHistory : Screen("attendance_history", "Attendance History", Icons.Default.DateRange)
    object Leave : Screen("leave", "Leaves", Icons.Default.Receipt)
    object FieldDuty : Screen("field_duty", "Field Duty", Icons.Default.Work)
    object CallCapture : Screen("call_capture", "Log Visit", Icons.Default.LocationOn)
    object DoctorPlanner : Screen("doctor_planner", "Doctor Planner", Icons.Default.Groups)
    object VisitList : Screen("visit_list", "Today's Visits", Icons.Default.LocationOn)
    object TaskList : Screen("task_list", "Tasks", Icons.Default.Task)
    object Chat : Screen("chat", "Messages", Icons.Default.Chat)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    // Admin & Executive Screens
    object AdminDashboard : Screen("admin_dashboard", "Admin Portal", Icons.Default.Business)
    object EmployeeDirectory : Screen("employee_directory", "Employees", Icons.Default.Groups)
    object AdminAttendance : Screen("admin_attendance", "Live Attendance", Icons.Default.DateRange)
    object LeaveApprovals : Screen("leave_approvals", "Leave Approvals", Icons.Default.CheckCircle)
    object AdminTaskManager : Screen("admin_task_manager", "Team Tasks", Icons.Default.Task)
    object OfficeLocations : Screen("office_locations", "Offices & Geofences", Icons.Default.LocationOn)
    object FieldOpsMap : Screen("field_ops_map", "Live Field Map", Icons.Default.Map)
    object ExecutiveOverview : Screen("executive_overview", "Executive Overview", Icons.Default.Assessment)
    object OrgHierarchy : Screen("org_hierarchy", "Org Chart", Icons.Default.Groups)
    object AdminSettings : Screen("admin_settings", "Settings", Icons.Default.Settings)
}
