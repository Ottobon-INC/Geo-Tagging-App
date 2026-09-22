package com.orcalabs.hrms.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    currentRoute: String?,
    currentUser: Employee?,
    onNavigate: (String) -> Unit,
    onSwitchRole: (String) -> Unit,
    onLogout: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isAuthScreen = currentRoute == Screen.Login.route

    val screenTitle = when (currentRoute) {
        Screen.Dashboard.route -> "Orca HRMS"
        Screen.Punch.route -> "Punch Attendance"
        Screen.AttendanceHistory.route -> "Attendance History"
        Screen.Leave.route -> "Leave Management"
        Screen.FieldDuty.route -> "Field Force Ops"
        Screen.CallCapture.route -> "Record Doctor / Chemist Visit"
        Screen.DoctorPlanner.route -> "Doctor Visit Planner"
        Screen.VisitList.route -> "Today's Field Visits"
        Screen.TaskList.route -> "My Tasks"
        Screen.Chat.route -> "Team Messages"
        Screen.Profile.route -> "My Profile"
        Screen.AdminDashboard.route -> "Admin Operations Portal"
        Screen.EmployeeDirectory.route -> "Employee Directory"
        Screen.AdminAttendance.route -> "Realtime Attendance"
        Screen.LeaveApprovals.route -> "Pending Leave Requests"
        Screen.AdminTaskManager.route -> "Team Task Delegation"
        Screen.OfficeLocations.route -> "Offices & Geofences"
        Screen.FieldOpsMap.route -> "Live Field Operations Map"
        Screen.ExecutiveOverview.route -> "Executive Overview"
        Screen.OrgHierarchy.route -> "Organization Hierarchy"
        Screen.AdminSettings.route -> "Enterprise Settings"
        else -> "Orca HRMS"
    }

    if (isAuthScreen) {
        // Fullscreen for login
        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
            content(Modifier.padding(padding))
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    currentUser = currentUser,
                    currentRoute = currentRoute,
                    onNavigate = onNavigate,
                    onSwitchRole = onSwitchRole,
                    onLogout = onLogout,
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = screenTitle,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = SlateDark
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = SlateDark)
                            }
                        },
                        actions = {
                            IconButton(onClick = { onNavigate(Screen.Chat.route) }) {
                                Icon(Icons.Default.Notifications, contentDescription = "Alerts", tint = SlateDark)
                            }
                            currentUser?.let {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable { onNavigate(Screen.Profile.route) }
                                ) {
                                    EmployeeAvatar(name = it.fullName, sizeDp = 34)
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = SurfaceWhite
                        )
                    )
                },
                bottomBar = {
                    currentUser?.let { user ->
                        BottomNavBar(
                            currentRoute = currentRoute,
                            userRole = user.role,
                            onNavigate = onNavigate
                        )
                    }
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { padding ->
                content(Modifier.padding(padding))
            }
        }
    }
}
