package com.orcalabs.hrms.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.UserRole
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun AppDrawer(
    currentUser: Employee?,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onSwitchRole: (String) -> Unit,
    onLogout: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(320.dp),
        drawerContainerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header: User Profile
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TealContainer)
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        EmployeeAvatar(
                            name = currentUser?.fullName ?: "Guest",
                            sizeDp = 52
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = currentUser?.fullName ?: "Not Logged In",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SlateDark
                            )
                            Text(
                                text = currentUser?.designation ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateTextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            currentUser?.let { RoleBadge(role = it.role) }
                        }
                    }
                    if (currentUser?.zone != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Zone: ${currentUser.zone} | HQ: ${currentUser.headquarter ?: "Corporate"}",
                            fontSize = 11.sp,
                            color = TealPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Quick Role Switcher (Essential for Pair-Testing All Roles Without Logout)
            Surface(
                color = Color(0xFFF1F5F9),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Switch Test Role", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                        }
                        Text("Instant", fontSize = 10.sp, color = SlateTextSecondary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(
                            Triple("BE", "OL010", "Field"),
                            Triple("RSM", "OL005", "Manager"),
                            Triple("ZSM", "OL003", "Zonal"),
                            Triple("HR", "OL009", "HR"),
                            Triple("GM", "OL026", "Executive")
                        ).forEach { (roleLabel, empId, _) ->
                            val isSelected = currentUser?.id == empId
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (isSelected) TealPrimary else Color.White,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .clickable { onSwitchRole(empId) }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = roleLabel,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else SlateDark
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = "PERSONAL & FIELD OPERATIONS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SlateTextSecondary,
                modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 4.dp)
            )

            val fieldNavItems = listOf(
                Screen.Dashboard,
                Screen.Punch,
                Screen.AttendanceHistory,
                Screen.Leave,
                Screen.FieldDuty,
                Screen.DoctorPlanner,
                Screen.TaskList,
                Screen.Chat,
                Screen.Profile
            )

            fieldNavItems.forEach { screen ->
                DrawerItem(
                    screen = screen,
                    isSelected = currentRoute == screen.route,
                    onClick = {
                        onNavigate(screen.route)
                        closeDrawer()
                    }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = SlateBorder)

            Text(
                text = "MANAGEMENT & GOVERNANCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SlateTextSecondary,
                modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 4.dp)
            )

            val adminNavItems = listOf(
                Screen.AdminDashboard,
                Screen.EmployeeDirectory,
                Screen.AdminAttendance,
                Screen.LeaveApprovals,
                Screen.AdminTaskManager,
                Screen.OfficeLocations,
                Screen.FieldOpsMap,
                Screen.ExecutiveOverview,
                Screen.OrgHierarchy,
                Screen.AdminSettings
            )

            adminNavItems.forEach { screen ->
                DrawerItem(
                    screen = screen,
                    isSelected = currentRoute == screen.route,
                    onClick = {
                        onNavigate(screen.route)
                        closeDrawer()
                    }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = SlateBorder)

            // Logout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLogout()
                        closeDrawer()
                    }
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color(0xFFEF4444))
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Sign Out",
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DrawerItem(
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(screen.title, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
        icon = { Icon(screen.icon, contentDescription = null, modifier = Modifier.size(20.dp)) },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = TealSoft,
            selectedIconColor = TealPrimary,
            selectedTextColor = TealPrimary,
            unselectedIconColor = SlateTextSecondary,
            unselectedTextColor = SlateDark
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
    )
}
