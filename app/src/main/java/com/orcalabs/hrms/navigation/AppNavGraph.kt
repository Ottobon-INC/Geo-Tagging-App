package com.orcalabs.hrms.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import com.orcalabs.hrms.domain.repository.OfficeRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import com.orcalabs.hrms.ui.screens.admin.AdminAttendanceScreen
import com.orcalabs.hrms.ui.screens.admin.AdminDashboardScreen
import com.orcalabs.hrms.ui.screens.admin.AdminDashboardViewModel
import com.orcalabs.hrms.ui.screens.admin.AdminSettingsScreen
import com.orcalabs.hrms.ui.screens.admin.AdminTaskManagerScreen
import com.orcalabs.hrms.ui.screens.admin.EmployeeDirectoryScreen
import com.orcalabs.hrms.ui.screens.admin.ExecutiveOverviewScreen
import com.orcalabs.hrms.ui.screens.admin.ExecutiveViewModel
import com.orcalabs.hrms.ui.screens.admin.FieldOpsMapScreen
import com.orcalabs.hrms.ui.screens.admin.LeaveApprovalScreen
import com.orcalabs.hrms.ui.screens.admin.OrgHierarchyScreen
import com.orcalabs.hrms.ui.screens.admin.OfficeLocationsScreen
import com.orcalabs.hrms.ui.screens.attendance.AttendanceHistoryScreen
import com.orcalabs.hrms.ui.screens.attendance.AttendanceHistoryViewModel
import com.orcalabs.hrms.ui.screens.attendance.PunchScreen
import com.orcalabs.hrms.ui.screens.attendance.PunchViewModel
import com.orcalabs.hrms.ui.screens.auth.LoginScreen
import com.orcalabs.hrms.ui.screens.auth.LoginViewModel
import com.orcalabs.hrms.ui.screens.chat.ChatScreen
import com.orcalabs.hrms.ui.screens.chat.ChatViewModel
import com.orcalabs.hrms.ui.screens.dashboard.DashboardScreen
import com.orcalabs.hrms.ui.screens.dashboard.DashboardViewModel
import com.orcalabs.hrms.ui.screens.fieldduty.CallCaptureScreen
import com.orcalabs.hrms.ui.screens.fieldduty.DoctorPlannerScreen
import com.orcalabs.hrms.ui.screens.fieldduty.FieldDutyScreen
import com.orcalabs.hrms.ui.screens.fieldduty.FieldDutyViewModel
import com.orcalabs.hrms.ui.screens.leave.LeaveScreen
import com.orcalabs.hrms.ui.screens.leave.LeaveViewModel
import com.orcalabs.hrms.ui.screens.profile.ProfileScreen
import com.orcalabs.hrms.ui.screens.tasks.TaskListScreen
import com.orcalabs.hrms.ui.screens.tasks.TaskViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authRepository: AuthRepository,
    attendanceRepository: AttendanceRepository,
    leaveRepository: LeaveRepository,
    fieldDutyRepository: FieldDutyRepository,
    taskRepository: TaskRepository,
    employeeRepository: EmployeeRepository,
    officeRepository: OfficeRepository,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            val vm: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = vm,
                onLoginSuccess = { user ->
                    if (user.role.isAdminOrExecutive) {
                        navController.navigate(Screen.AdminDashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            val vm: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                viewModel = vm,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Punch.route) {
            val vm: PunchViewModel = hiltViewModel()
            PunchScreen(
                viewModel = vm,
                onNavigateHistory = { navController.navigate(Screen.AttendanceHistory.route) }
            )
        }

        composable(Screen.AttendanceHistory.route) {
            val vm: AttendanceHistoryViewModel = hiltViewModel()
            AttendanceHistoryScreen(viewModel = vm)
        }

        composable(Screen.Leave.route) {
            val vm: LeaveViewModel = hiltViewModel()
            LeaveScreen(viewModel = vm)
        }

        composable(Screen.FieldDuty.route) {
            val vm: FieldDutyViewModel = hiltViewModel()
            FieldDutyScreen(
                viewModel = vm,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.CallCapture.route) {
            val vm: FieldDutyViewModel = hiltViewModel()
            CallCaptureScreen(
                viewModel = vm,
                onCallSaved = { navController.popBackStack() }
            )
        }

        composable(Screen.DoctorPlanner.route) {
            val vm: FieldDutyViewModel = hiltViewModel()
            DoctorPlannerScreen(
                viewModel = vm,
                onLogCall = { navController.navigate(Screen.CallCapture.route) }
            )
        }

        composable(Screen.VisitList.route) {
            val vm: FieldDutyViewModel = hiltViewModel()
            FieldDutyScreen(
                viewModel = vm,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.TaskList.route) {
            val vm: TaskViewModel = hiltViewModel()
            TaskListScreen(viewModel = vm)
        }

        composable(Screen.Chat.route) {
            val vm: ChatViewModel = hiltViewModel()
            ChatScreen(viewModel = vm)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                authRepository = authRepository,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Admin Screens
        composable(Screen.AdminDashboard.route) {
            val vm: AdminDashboardViewModel = hiltViewModel()
            AdminDashboardScreen(
                viewModel = vm,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.EmployeeDirectory.route) {
            EmployeeDirectoryScreen(employeeRepository = employeeRepository)
        }

        composable(Screen.AdminAttendance.route) {
            AdminAttendanceScreen(employeeRepository = employeeRepository)
        }

        composable(Screen.LeaveApprovals.route) {
            LeaveApprovalScreen(
                leaveRepository = leaveRepository,
                authRepository = authRepository
            )
        }

        composable(Screen.AdminTaskManager.route) {
            AdminTaskManagerScreen(
                taskRepository = taskRepository,
                authRepository = authRepository
            )
        }

        composable(Screen.OfficeLocations.route) {
            OfficeLocationsScreen(officeRepository = officeRepository)
        }

        composable(Screen.FieldOpsMap.route) {
            FieldOpsMapScreen()
        }

        composable(Screen.ExecutiveOverview.route) {
            val vm: ExecutiveViewModel = hiltViewModel()
            ExecutiveOverviewScreen(viewModel = vm)
        }

        composable(Screen.OrgHierarchy.route) {
            OrgHierarchyScreen()
        }

        composable(Screen.AdminSettings.route) {
            AdminSettingsScreen()
        }
    }
}
