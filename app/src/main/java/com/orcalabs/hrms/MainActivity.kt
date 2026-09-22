package com.orcalabs.hrms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.domain.repository.FieldDutyRepository
import com.orcalabs.hrms.domain.repository.LeaveRepository
import com.orcalabs.hrms.domain.repository.OfficeRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import com.orcalabs.hrms.navigation.AppNavGraph
import com.orcalabs.hrms.navigation.MainScaffold
import com.orcalabs.hrms.navigation.Screen
import com.orcalabs.hrms.ui.theme.OrcaHRMSTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var attendanceRepository: AttendanceRepository

    @Inject
    lateinit var leaveRepository: LeaveRepository

    @Inject
    lateinit var fieldDutyRepository: FieldDutyRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var employeeRepository: EmployeeRepository

    @Inject
    lateinit var officeRepository: OfficeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrcaHRMSTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val currentUser by authRepository.currentUser.collectAsState()
                val scope = rememberCoroutineScope()

                MainScaffold(
                    currentRoute = currentRoute,
                    currentUser = currentUser,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onSwitchRole = { empId ->
                        scope.launch {
                            authRepository.switchUserRoleForTesting(empId)
                            val switchedUser = authRepository.currentUser.value
                            if (switchedUser?.role?.isAdminOrExecutive == true) {
                                navController.navigate(Screen.AdminDashboard.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    },
                    onLogout = {
                        scope.launch {
                            authRepository.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                ) { modifier ->
                    AppNavGraph(
                        navController = navController,
                        authRepository = authRepository,
                        attendanceRepository = attendanceRepository,
                        leaveRepository = leaveRepository,
                        fieldDutyRepository = fieldDutyRepository,
                        taskRepository = taskRepository,
                        employeeRepository = employeeRepository,
                        officeRepository = officeRepository,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
