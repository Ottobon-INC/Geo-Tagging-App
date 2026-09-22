package com.orcalabs.hrms.ui.screens.attendance

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.AttendanceBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun AttendanceHistoryScreen(
    viewModel: AttendanceHistoryViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Month Summary Header
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
                    Text(
                        text = "September 2026 Overview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = SlateDark
                    )
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = TealPrimary)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    SummaryPill(count = uiState.totalPresent, label = "Present", color = StatusPresent)
                    SummaryPill(count = uiState.totalFieldDuty, label = "Field Duty", color = Color(0xFF0EA5E9))
                    SummaryPill(count = uiState.totalHalfDay, label = "Half Day", color = Color(0xFFF59E0B))
                    SummaryPill(count = uiState.totalLeaves, label = "Leaves", color = Color(0xFF8B5CF6))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Pair("ALL", "All Logs"),
                Pair("PRESENT", "Present"),
                Pair("FIELD_DUTY", "Field Duty"),
                Pair("LEAVE", "Leaves")
            ).forEach { (key, label) ->
                val isSelected = uiState.selectedFilter == key
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) TealPrimary else SurfaceWhite,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealPrimary else SlateBorder),
                    modifier = Modifier.clickable { viewModel.setFilter(key) }
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) Color.White else SlateDark,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Attendance List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.filteredRecords) { record ->
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
                            Text(
                                text = record.date,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = SlateDark
                            )
                            AttendanceBadge(status = record.status)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "In: ${record.punchInTime ?: "--:--"} • Out: ${record.punchOutTime ?: "--:--"}",
                                    fontSize = 12.sp,
                                    color = SlateDark
                                )
                            }
                            Text(
                                text = "${record.totalHours} hrs",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = TealPrimary
                            )
                        }

                        if (record.punchInLocation != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = SlateTextSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = record.punchInLocation.address,
                                    fontSize = 11.sp,
                                    color = SlateTextSecondary,
                                    maxLines = 1
                                )
                            }
                        }

                        if (record.remarks != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Note: ${record.remarks}",
                                fontSize = 11.sp,
                                color = Color(0xFF6B7280),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryPill(count: Int, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$count",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = SlateTextSecondary
        )
    }
}
