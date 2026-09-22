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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
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
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.UserRole
import com.orcalabs.hrms.domain.repository.EmployeeRepository
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary

@Composable
fun EmployeeDirectoryScreen(
    employeeRepository: EmployeeRepository,
    modifier: Modifier = Modifier
) {
    val employees by employeeRepository.getAllEmployees().collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var selectedRoleFilter by remember { mutableStateOf<UserRole?>(null) }

    val filtered = employees.filter { emp ->
        val matchesQuery = emp.fullName.contains(searchQuery, ignoreCase = true) ||
                emp.id.contains(searchQuery, ignoreCase = true) ||
                emp.designation.contains(searchQuery, ignoreCase = true) ||
                (emp.headquarter?.contains(searchQuery, ignoreCase = true) == true)
        val matchesRole = selectedRoleFilter == null || emp.role == selectedRoleFilter
        matchesQuery && matchesRole
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by name, ID (e.g. OL010), HQ...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TealPrimary) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Role Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(null, UserRole.BE, UserRole.RSM, UserRole.ZSM, UserRole.HR).forEach { role ->
                val isSelected = selectedRoleFilter == role
                val label = role?.name ?: "All (${employees.size})"
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) TealPrimary else SurfaceWhite,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealPrimary else SlateBorder),
                    modifier = Modifier.clickable { selectedRoleFilter = role }
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) Color.White else SlateDark,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Employee Cards List
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
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            EmployeeAvatar(name = emp.fullName, sizeDp = 44)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = emp.fullName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = SlateDark
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "(${emp.id})",
                                        fontSize = 11.sp,
                                        color = SlateTextSecondary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = emp.designation,
                                    fontSize = 12.sp,
                                    color = SlateTextSecondary
                                )
                            }
                            RoleBadge(role = emp.role)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${emp.headquarter ?: "Corporate"} (${emp.zone ?: "HQ"})",
                                    fontSize = 11.sp,
                                    color = SlateDark
                                )
                            }

                            StatusBadge(
                                label = if (emp.isPunchInToday) "Present (${emp.lastPunchTime})" else "Not Punched",
                                textColor = if (emp.isPunchInToday) StatusPresent else Color(0xFFEF4444),
                                bgColor = if (emp.isPunchInToday) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                            )
                        }

                        if (emp.reportsToName != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Reports To: ${emp.reportsToName}",
                                fontSize = 11.sp,
                                color = SlateTextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Email, contentDescription = null, tint = SlateTextSecondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(emp.email, fontSize = 10.sp, color = SlateTextSecondary)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Call, contentDescription = null, tint = SlateTextSecondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(emp.phone, fontSize = 10.sp, color = SlateTextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}
