package com.orcalabs.hrms.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.navigation.Screen
import com.orcalabs.hrms.ui.common.MetricCard
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun AdminDashboardScreen(
    viewModel: AdminDashboardViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val exec = uiState.executiveData

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Welcome Banner
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Pharma Operations Portal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SlateDark
                    )
                    Text(
                        text = "Orca Labs Corporate Management Dashboard",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateTextSecondary
                    )
                }
                uiState.currentUser?.let { RoleBadge(role = it.role) }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Operations Metrics Grid
        Text("ENTERPRISE METRICS (TODAY)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateTextSecondary)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Total Headcount",
                value = "${exec.totalHeadcount}",
                subtitle = "${exec.presentToday} Present Today",
                icon = Icons.Default.Groups,
                accentColor = TealPrimary,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.EmployeeDirectory.route) }
            )
            MetricCard(
                title = "Active On Field",
                value = "${exec.onFieldToday}",
                subtitle = "Touring Clinics",
                icon = Icons.Default.Map,
                accentColor = Color(0xFF0EA5E9),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.FieldOpsMap.route) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Doctor Calls Today",
                value = "${exec.totalDoctorCallsToday}",
                subtitle = "Across all territories",
                icon = Icons.Default.MedicalServices,
                accentColor = IndigoAccent,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.ExecutiveOverview.route) }
            )
            MetricCard(
                title = "Pending Leaves",
                value = "${uiState.pendingLeavesCount}",
                subtitle = "Awaiting Approval",
                icon = Icons.Default.CheckCircle,
                accentColor = Color(0xFFF97316),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.LeaveApprovals.route) }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Administrative Tools
        SectionHeader(title = "Administrative Modules")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminModuleTile(
                    label = "Employee Directory",
                    sub = "35 Staff (BE, RSM, ZSM)",
                    icon = Icons.Default.Groups,
                    color = TealPrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.EmployeeDirectory.route) }
                )
                AdminModuleTile(
                    label = "Live Attendance",
                    sub = "Realtime roll-call",
                    icon = Icons.Default.DateRange,
                    color = Color(0xFF0EA5E9),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.AdminAttendance.route) }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminModuleTile(
                    label = "Leave Approvals",
                    sub = "${uiState.pendingLeavesCount} Requests waiting",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFFF97316),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.LeaveApprovals.route) }
                )
                AdminModuleTile(
                    label = "Field Ops Map",
                    sub = "Live GPS rep tracking",
                    icon = Icons.Default.Map,
                    color = Color(0xFF10B981),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.FieldOpsMap.route) }
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AdminModuleTile(
                    label = "Office Geofences",
                    sub = "4 Office coordinates",
                    icon = Icons.Default.LocationOn,
                    color = IndigoAccent,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.OfficeLocations.route) }
                )
                AdminModuleTile(
                    label = "Org Hierarchy",
                    sub = "Reporting tree",
                    icon = Icons.Default.Assessment,
                    color = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Screen.OrgHierarchy.route) }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Zone Performance Breakdown
        SectionHeader(
            title = "Territory & Zone Breakdown",
            actionText = "Full Report",
            onActionClick = { onNavigate(Screen.ExecutiveOverview.route) }
        )

        exec.zones.forEach { zone ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = zone.zoneName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = SlateDark
                        )
                        Text(
                            text = "₹${(zone.totalPobAmountInr / 1000).toInt()}k / ₹${(zone.targetPobInr / 1000).toInt()}k",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = TealPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Staff: ${zone.presentToday}/${zone.totalEmployees} Present",
                            fontSize = 11.sp,
                            color = SlateTextSecondary
                        )
                        Text(
                            text = "Calls: ${zone.totalCallsToday}",
                            fontSize = 11.sp,
                            color = SlateDark
                        )
                        Text(
                            text = "Active Field: ${zone.activeOnField}",
                            fontSize = 11.sp,
                            color = StatusPresent
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AdminModuleTile(
    label: String,
    sub: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceWhite,
        shadowElevation = 1.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SlateDark)
                Text(sub, fontSize = 10.sp, color = SlateTextSecondary, maxLines = 1)
            }
        }
    }
}
