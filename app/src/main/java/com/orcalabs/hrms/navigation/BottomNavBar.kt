package com.orcalabs.hrms.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.UserRole
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun BottomNavBar(
    currentRoute: String?,
    userRole: UserRole,
    onNavigate: (String) -> Unit
) {
    val items: List<Screen> = when {
        userRole.isAdminOrExecutive -> listOf(
            Screen.AdminDashboard,
            Screen.EmployeeDirectory,
            Screen.LeaveApprovals,
            Screen.FieldOpsMap,
            Screen.ExecutiveOverview
        )
        userRole.isManager -> listOf(
            Screen.Dashboard,
            Screen.AdminAttendance,
            Screen.LeaveApprovals,
            Screen.AdminTaskManager,
            Screen.Chat
        )
        else -> listOf(
            Screen.Dashboard,
            Screen.Punch,
            Screen.FieldDuty,
            Screen.TaskList,
            Screen.Chat
        )
    }

    NavigationBar(
        containerColor = SurfaceWhite,
        contentColor = SlateTextSecondary,
        tonalElevation = androidx.compose.ui.unit.Dp(8f)
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TealPrimary,
                    selectedTextColor = TealPrimary,
                    indicatorColor = TealSoft,
                    unselectedIconColor = SlateTextSecondary,
                    unselectedTextColor = SlateTextSecondary
                )
            )
        }
    }
}
