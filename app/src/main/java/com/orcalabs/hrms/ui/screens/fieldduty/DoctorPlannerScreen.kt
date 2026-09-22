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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.DoctorPlanItem
import com.orcalabs.hrms.navigation.Screen
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun DoctorPlannerScreen(
    viewModel: FieldDutyViewModel,
    onLogCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredDoctors = uiState.plannedDoctors.filter {
        it.doctorName.contains(searchQuery, ignoreCase = true) ||
        it.specialty.contains(searchQuery, ignoreCase = true) ||
        it.hospitalName.contains(searchQuery, ignoreCase = true) ||
        it.area.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search doctor, clinic or area...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TealPrimary) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Progress Summary
        val visitedCount = uiState.plannedDoctors.count { it.isVisitedToday }
        val totalCount = uiState.plannedDoctors.size

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = SurfaceWhite,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Today's Tour Plan Progress",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = SlateDark
                    )
                    Text(
                        text = "$visitedCount of $totalCount Doctors Called",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
                StatusBadge(
                    label = if (visitedCount == totalCount) "Completed" else "${totalCount - visitedCount} Remaining",
                    textColor = if (visitedCount == totalCount) StatusPresent else TealPrimary,
                    bgColor = if (visitedCount == totalCount) Color(0xFFECFDF5) else TealSoft
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Doctors List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredDoctors) { doctor ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (doctor.isVisitedToday) Color(0xFFECFDF5) else TealSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (doctor.isVisitedToday) Icons.Default.CheckCircle else Icons.Default.MedicalServices,
                                contentDescription = null,
                                tint = if (doctor.isVisitedToday) StatusPresent else TealPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = doctor.doctorName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = SlateDark
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                StatusBadge(label = "Class ${doctor.priority}", textColor = IndigoAccent, bgColor = Color(0xFFEEF2FF))
                            }
                            Text(
                                text = "${doctor.specialty} • ${doctor.hospitalName}",
                                fontSize = 12.sp,
                                color = SlateTextSecondary
                            )
                            Text(
                                text = "📍 ${doctor.area}",
                                fontSize = 11.sp,
                                color = TealPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        if (!doctor.isVisitedToday) {
                            Button(
                                onClick = {
                                    viewModel.selectDoctorFromPlanner(doctor)
                                    onLogCall()
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                            ) {
                                Text("Call", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            StatusBadge(label = "Called", textColor = StatusPresent, bgColor = Color(0xFFECFDF5))
                        }
                    }
                }
            }
        }
    }
}
