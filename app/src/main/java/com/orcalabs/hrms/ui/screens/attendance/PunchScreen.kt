package com.orcalabs.hrms.ui.screens.attendance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun PunchScreen(
    viewModel: PunchViewModel,
    onNavigateHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isPunchedIn = uiState.punchState.isPunchedIn

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Digital Live Clock
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.currentDateString,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiState.currentTimeString.ifEmpty { "09:30:00 AM" },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = TealPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Geofence & Location Verification Status
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (uiState.isGeofenceValid) Color(0xFFECFDF5) else Color(0xFFFEF2F2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = if (uiState.isGeofenceValid) StatusPresent else Color(0xFFEF4444),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (uiState.isGeofenceValid) "Inside Geofence" else "Outside Geofence",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = SlateDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        StatusBadge(
                            label = "${uiState.distanceMeters}m away",
                            textColor = if (uiState.isGeofenceValid) StatusPresent else Color(0xFFEF4444),
                            bgColor = if (uiState.isGeofenceValid) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                        )
                    }
                    Text(
                        text = uiState.officeName,
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Selfie Verification Frame
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TealContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(2.dp, TealPrimary, CircleShape)
                        .background(Color.White)
                        .clickable { viewModel.captureSelfie() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Selfie Verified",
                        tint = TealPrimary,
                        modifier = Modifier.size(54.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusPresent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Face ID & Liveness Verified",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = SlateDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Giant Circular Punch Button
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    if (isPunchedIn) Color(0xFFEF4444) else TealPrimary
                )
                .clickable(enabled = !uiState.isPunching) { viewModel.executePunch() },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isPunching) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Punch",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isPunchedIn) "PUNCH OUT" else "PUNCH IN",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        AnimatedVisibility(visible = uiState.punchSuccessMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.punchSuccessMessage ?: "",
                color = StatusPresent,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Today's Punch Timeline
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Attendance Timeline",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = SlateDark
                    )
                    Text(
                        text = "View History",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = TealPrimary,
                        modifier = Modifier.clickable { onNavigateHistory() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Punch In", fontSize = 11.sp, color = SlateTextSecondary)
                        Text(
                            text = uiState.punchState.punchInTime ?: "--:--",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateDark
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Punch Out", fontSize = 11.sp, color = SlateTextSecondary)
                        Text(
                            text = uiState.punchState.punchOutTime ?: "--:--",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateDark
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Working Hours", fontSize = 11.sp, color = SlateTextSecondary)
                        Text(
                            text = uiState.punchState.activeDurationFormatted,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )
                    }
                }
            }
        }
    }
}
