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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.AttendanceStatus
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.domain.repository.AttendanceRepository
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.ui.common.AttendanceBadge
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary

@Composable
fun AdminAttendanceScreen(
    employeeRepository: EmployeeRepository,
    modifier: Modifier = Modifier
) {
    val employees by employeeRepository.getAllEmployees().collectAsState(initial = emptyList())
    var filterStatus by remember { mutableStateOf<String>("ALL") }

    val filtered = employees.filter { emp ->
        when (filterStatus) {
            "PRESENT" -> emp.isPunchInToday
            "ABSENT" -> !emp.isPunchInToday
            else -> true
        }
    }

    val presentCount = employees.count { it.isPunchInToday }
    val absentCount = employees.size - presentCount

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Attendance Roll-call Header
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
                        text = "Live Attendance Roll-Call",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = SlateDark
                    )
                    Text(
                        text = "Today • Realtime GPS Geofence Feeds",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = Color(0xFFECFDF5), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = "$presentCount In",
                            color = StatusPresent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(color = Color(0xFFFEF2F2), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = "$absentCount Out",
                            color = Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Filter chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Pair("ALL", "All Staff (${employees.size})"),
                Pair("PRESENT", "Punched In ($presentCount)"),
                Pair("ABSENT", "Not Punched ($absentCount)")
            ).forEach { (key, label) ->
                val isSel = filterStatus == key
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSel) TealPrimary else SurfaceWhite,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSel) TealPrimary else SlateBorder),
                    modifier = Modifier.clickable { filterStatus = key }
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSel) Color.White else SlateDark,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Roll call list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filtered) { emp ->
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
                        EmployeeAvatar(name = emp.fullName, sizeDp = 42)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = emp.fullName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = SlateDark
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                RoleBadge(role = emp.role)
                            }
                            Text(
                                text = "${emp.designation} • ${emp.headquarter ?: "HQ"}",
                                fontSize = 11.sp,
                                color = SlateTextSecondary
                            )
                            if (emp.isPunchInToday) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = StatusPresent, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "In at ${emp.lastPunchTime} • GPS Verified",
                                        fontSize = 11.sp,
                                        color = StatusPresent,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        if (emp.isPunchInToday) {
                            AttendanceBadge(status = AttendanceStatus.PRESENT)
                        } else {
                            AttendanceBadge(status = AttendanceStatus.ABSENT)
                        }
                    }
                }
            }
        }
    }
}
