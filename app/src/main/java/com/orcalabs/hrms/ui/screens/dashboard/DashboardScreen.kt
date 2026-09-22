package com.orcalabs.hrms.ui.screens.dashboard

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
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.common.MetricCard
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.StatusPresentSoft
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val user = uiState.currentUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Welcome Header
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EmployeeAvatar(name = user?.fullName ?: "Orca Employee", sizeDp = 48)
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hello, ${user?.fullName ?: "Employee"}!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SlateDark
                    )
                    Text(
                        text = "${user?.designation ?: "Business Executive"} • ${user?.headquarter ?: "Hyderabad"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateTextSecondary
                    )
                }
                user?.let { RoleBadge(role = it.role) }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Punch In Status Banner
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (uiState.punchState.isPunchedIn) TealContainer else Color(0xFFFFF7ED)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                if (uiState.punchState.isPunchedIn) TealPrimary else Color(0xFFEA580C)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (uiState.punchState.isPunchedIn) "Punched In Today" else "Not Punched In",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = SlateDark
                        )
                        Text(
                            text = if (uiState.punchState.isPunchedIn)
                                "Since ${uiState.punchState.punchInTime} • ${uiState.punchState.activeDurationFormatted}"
                            else "Geofence ready at Orca HQ",
                            fontSize = 12.sp,
                            color = SlateTextSecondary
                        )
                    }
                }

                Button(
                    onClick = { onNavigate(Screen.Punch.route) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.punchState.isPunchedIn) TealPrimary else Color(0xFFEA580C)
                    )
                ) {
                    Text(
                        text = if (uiState.punchState.isPunchedIn) "Punch Out" else "Punch In",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Pharma KPI 2x2 Grid
        Text(
            text = "TODAY'S HIGHLIGHTS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Field Calls",
                value = "${uiState.completedVisitsToday.size} / 8",
                subtitle = "Visits Completed",
                icon = Icons.Default.MedicalServices,
                accentColor = TealPrimary,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.VisitList.route) }
            )
            MetricCard(
                title = "POB Booked",
                value = "₹28,500",
                subtitle = "Chemist Orders",
                icon = Icons.Default.CurrencyRupee,
                accentColor = IndigoAccent,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.FieldDuty.route) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Pending Tasks",
                value = "${uiState.pendingTasks.count { it.status.name != "COMPLETED" }}",
                subtitle = "2 High Priority",
                icon = Icons.Default.Task,
                accentColor = Color(0xFFF97316),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.TaskList.route) }
            )
            MetricCard(
                title = "Leave Balance",
                value = "${uiState.leaveBalance.totalRemaining} Days",
                subtitle = "Available Leaves",
                icon = Icons.Default.CalendarMonth,
                accentColor = Color(0xFF10B981),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Leave.route) }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Action Tiles
        SectionHeader(title = "Quick Actions")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionButton(
                label = "Log Visit",
                icon = Icons.Default.AddLocationAlt,
                color = TealPrimary,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.CallCapture.route) }
            )
            QuickActionButton(
                label = "Doctor Plan",
                icon = Icons.Default.MedicalServices,
                color = IndigoAccent,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.DoctorPlanner.route) }
            )
            QuickActionButton(
                label = "Apply Leave",
                icon = Icons.Default.DateRange,
                color = Color(0xFF0EA5E9),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Leave.route) }
            )
            QuickActionButton(
                label = "History",
                icon = Icons.Default.CheckCircle,
                color = Color(0xFF8B5CF6),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.AttendanceHistory.route) }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Doctor Planner Next Appointment
        SectionHeader(
            title = "Next Planned Doctor Visit",
            actionText = "View All",
            onActionClick = { onNavigate(Screen.DoctorPlanner.route) }
        )

        val nextDoctor = uiState.plannedDoctors.firstOrNull { !it.isVisitedToday }
        if (nextDoctor != null) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.CallCapture.route) }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(IndigoAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = IndigoAccent,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = nextDoctor.doctorName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SlateDark
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            StatusBadge(label = nextDoctor.priority, textColor = IndigoAccent, bgColor = Color(0xFFEEF2FF))
                        }
                        Text(
                            text = "${nextDoctor.specialty} • ${nextDoctor.hospitalName}",
                            fontSize = 12.sp,
                            color = SlateTextSecondary
                        )
                        Text(
                            text = "📍 ${nextDoctor.area}",
                            fontSize = 11.sp,
                            color = TealPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Button(
                        onClick = { onNavigate(Screen.CallCapture.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Log Call", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SlateDark
            )
        }
    }
}
