package com.orcalabs.hrms.ui.screens.fieldduty

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.orcalabs.hrms.data.model.VisitType
import com.orcalabs.hrms.navigation.Screen
import com.orcalabs.hrms.ui.common.MetricCard
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.IndigoSoft
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.StatusPresentSoft
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun FieldDutyScreen(
    viewModel: FieldDutyViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val session = uiState.activeSession
    val isSessionActive = session?.isSessionActive == true

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate(Screen.CallCapture.route) },
                containerColor = TealPrimary,
                contentColor = Color.White
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddLocationAlt, contentDescription = "Log Call")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Log Visit", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(padding)
                .padding(16.dp)
        ) {
            // Session Status Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceWhite,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (isSessionActive) StatusPresent else Color(0xFF94A3B8))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isSessionActive) "Field Session In Progress" else "Field Session Offline",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SlateDark
                            )
                        }
                        StatusBadge(
                            label = if (isSessionActive) "Tracking ON" else "Paused",
                            textColor = if (isSessionActive) StatusPresent else SlateTextSecondary,
                            bgColor = if (isSessionActive) StatusPresentSoft else Color(0xFFF1F5F9)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isSessionActive)
                            "Started at ${session.startTime} • Auto GPS Geo-tagging enabled for doctor & chemist calls."
                        else "Start your morning field session to begin recording doctor calls and chemist POB orders.",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (isSessionActive) viewModel.endSession() else viewModel.startSession()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSessionActive) Color(0xFFEF4444) else TealPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (isSessionActive) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSessionActive) "End Field Work Session" else "Start Morning Field Duty",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Metrics row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Visits Logged",
                    value = "${uiState.visitsToday.size}",
                    subtitle = "Calls Recorded Today",
                    icon = Icons.Default.MedicalServices,
                    accentColor = TealPrimary,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Total POB",
                    value = "₹${session?.totalPobBooked?.toInt() ?: 28500}",
                    subtitle = "Orders Generated",
                    icon = Icons.Default.CurrencyRupee,
                    accentColor = IndigoAccent,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            SectionHeader(
                title = "Calls Completed Today",
                actionText = "Doctor Planner",
                onActionClick = { onNavigate(Screen.DoctorPlanner.route) }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.visitsToday) { visit ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (visit.visitType == VisitType.DOCTOR) TealSoft else IndigoSoft
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (visit.visitType == VisitType.DOCTOR)
                                                Icons.Default.MedicalServices else Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = if (visit.visitType == VisitType.DOCTOR) TealPrimary else IndigoAccent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = visit.contactPerson,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = SlateDark
                                        )
                                        Text(
                                            text = visit.clinicOrShopName,
                                            fontSize = 11.sp,
                                            color = SlateTextSecondary
                                        )
                                    }
                                }
                                Text(
                                    text = visit.timestamp,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SlateTextSecondary
                                )
                            }

                            if (visit.productsPromoted.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Products Detailed: " + visit.productsPromoted.joinToString(", ") {
                                        "${it.productName}${if (it.samplesGiven > 0) " (${it.samplesGiven} samples)" else ""}"
                                    },
                                    fontSize = 11.sp,
                                    color = TealPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (visit.pobValueInr > 0) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Surface(
                                    color = Color(0xFFECFDF5),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "POB Booked: ₹${visit.pobValueInr.toInt()}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = StatusPresent,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Feedback: ${visit.doctorFeedback}",
                                fontSize = 11.sp,
                                color = SlateDark
                            )
                        }
                    }
                }
            }
        }
    }
}
