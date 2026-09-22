package com.orcalabs.hrms.ui.screens.tasks

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.TaskStatus
import com.orcalabs.hrms.ui.common.OrcaButton
import com.orcalabs.hrms.ui.common.PriorityBadge
import com.orcalabs.hrms.ui.common.TaskBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Pair("ALL", "All"),
                Pair("IN_PROGRESS", "In Progress"),
                Pair("PENDING", "To Do"),
                Pair("COMPLETED", "Done")
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

        // Task List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.filteredTasks) { task ->
                val isDone = task.status == TaskStatus.COMPLETED || task.status == TaskStatus.APPROVED
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
                            PriorityBadge(priority = task.priority)
                            TaskBadge(status = task.status)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = task.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = SlateDark
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = task.description,
                            fontSize = 12.sp,
                            color = SlateTextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = SlateTextSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = task.assignedByName,
                                    fontSize = 11.sp,
                                    color = SlateTextSecondary
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Due: ${task.dueDate}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TealPrimary
                                )
                            }
                        }

                        if (!isDone) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { viewModel.openCompleteDialog(task) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Mark as Completed", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        } else if (task.submissionNotes != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(color = Color(0xFFECFDF5), shape = RoundedCornerShape(6.dp), modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Completed: ${task.submissionNotes}",
                                    fontSize = 11.sp,
                                    color = StatusPresent,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Complete Task Dialog
    if (uiState.isCompleteDialogOpen) {
        AlertDialog(
            onDismissRequest = { viewModel.closeCompleteDialog() },
            title = { Text("Complete Task", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = uiState.taskToComplete?.title ?: "",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = SlateDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = uiState.completionNotes,
                        onValueChange = { viewModel.onNotesChange(it) },
                        label = { Text("Completion / Submission Notes") },
                        placeholder = { Text("e.g. Completed during morning clinic rounds...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                OrcaButton(
                    text = "Confirm Completion",
                    onClick = { viewModel.markTaskCompleted() }
                )
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.closeCompleteDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = SlateDark)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
