package com.orcalabs.hrms.ui.screens.leave

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.orcalabs.hrms.data.model.LeaveType
import com.orcalabs.hrms.ui.common.LeaveBadge
import com.orcalabs.hrms.ui.common.OrcaButton
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun LeaveScreen(
    viewModel: LeaveViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openApplyDialog() },
                containerColor = TealPrimary,
                contentColor = Color.White
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "Apply Leave")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Apply Leave", fontWeight = FontWeight.Bold)
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
            // Balance Cards Row
            Text(
                text = "YOUR LEAVE BALANCES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SlateTextSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LeaveBalanceCard(
                    title = "Casual",
                    available = uiState.balance.casualTotal - uiState.balance.casualUsed,
                    total = uiState.balance.casualTotal,
                    color = TealPrimary,
                    modifier = Modifier.weight(1f)
                )
                LeaveBalanceCard(
                    title = "Sick",
                    available = uiState.balance.sickTotal - uiState.balance.sickUsed,
                    total = uiState.balance.sickTotal,
                    color = IndigoAccent,
                    modifier = Modifier.weight(1f)
                )
                LeaveBalanceCard(
                    title = "Earned",
                    available = uiState.balance.earnedTotal - uiState.balance.earnedUsed,
                    total = uiState.balance.earnedTotal,
                    color = Color(0xFF0EA5E9),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            SectionHeader(title = "My Leave Requests")

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.myRequests) { req ->
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
                                    text = req.leaveType.displayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = SlateDark
                                )
                                LeaveBadge(status = req.status)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${req.startDate} to ${req.endDate} (${req.numberOfDays} days)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SlateDark
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Reason: ${req.reason}",
                                fontSize = 12.sp,
                                color = SlateTextSecondary
                            )

                            if (req.reviewRemarks != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Surface(
                                    color = Color(0xFFF1F5F9),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Reviewer (${req.reviewedBy}): ${req.reviewRemarks}",
                                        fontSize = 11.sp,
                                        color = SlateDark,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Apply Leave Dialog
    if (uiState.isApplyDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.closeApplyDialog() },
            title = { Text("Apply for Leave", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Select Leave Type:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(LeaveType.CASUAL, LeaveType.SICK, LeaveType.EARNED).forEach { type ->
                            val isSel = uiState.selectedLeaveType == type
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) TealPrimary else Color.White,
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSel) TealPrimary else SlateBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.onTypeSelected(type) }
                            ) {
                                Text(
                                    text = type.displayName.split(" ")[0],
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else SlateDark,
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = uiState.startDate,
                            onValueChange = { viewModel.onStartDateChange(it) },
                            label = { Text("Start Date") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = uiState.endDate,
                            onValueChange = { viewModel.onEndDateChange(it) },
                            label = { Text("End Date") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = uiState.reason,
                        onValueChange = { viewModel.onReasonChange(it) },
                        label = { Text("Reason for Leave") },
                        placeholder = { Text("Please explain the reason") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                OrcaButton(
                    text = "Submit Application",
                    onClick = { viewModel.submitLeave() },
                    enabled = !uiState.isSubmitting && uiState.reason.isNotBlank()
                )
            },
            dismissButton = {
                IconButton(onClick = { viewModel.closeApplyDialog() }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
            }
        )
    }
}

@Composable
private fun LeaveBalanceCard(
    title: String,
    available: Int,
    total: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceWhite,
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 11.sp, color = SlateTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$available",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = "of $total days",
                fontSize = 10.sp,
                color = SlateTextSecondary
            )
        }
    }
}
