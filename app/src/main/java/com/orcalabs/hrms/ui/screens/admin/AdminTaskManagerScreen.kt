package com.orcalabs.hrms.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.HrmsTask
import com.orcalabs.hrms.data.model.TaskPriority
import com.orcalabs.hrms.data.model.TaskStatus
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import com.orcalabs.hrms.ui.common.OrcaButton
import com.orcalabs.hrms.ui.common.PriorityBadge
import com.orcalabs.hrms.ui.common.TaskBadge
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealPrimary
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun AdminTaskManagerScreen(
    taskRepository: TaskRepository,
    authRepository: AuthRepository,
    modifier: Modifier = Modifier
) {
    val tasks by taskRepository.getAllTasks().collectAsState(initial = emptyList())
    val currentUser by authRepository.currentUser.collectAsState()
    val scope = rememberCoroutineScope()

    var isCreateDialogOpen by remember { mutableStateOf(false) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDesc by remember { mutableStateOf("") }
    var assignToName by remember { mutableStateOf("Sandeep Reddy G") }
    var assignToId by remember { mutableStateOf("OL010") }
    var dueDate by remember { mutableStateOf("2026-09-30") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isCreateDialogOpen = true },
                containerColor = TealPrimary,
                contentColor = Color.White
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "Delegate Task")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Delegate Task", fontWeight = FontWeight.Bold)
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
                        Text("Team Task Delegation", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SlateDark)
                        Text("Assign targets, DCR deadlines & clinical compliance", fontSize = 12.sp, color = SlateTextSecondary)
                    }
                    Text("${tasks.size} Active Tasks", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TealPrimary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(tasks) { task ->
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
                            Text(task.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SlateDark)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(task.description, fontSize = 12.sp, color = SlateTextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Assignee: ${task.assignedToName}", fontSize = 11.sp, color = SlateDark)
                                }
                                Text("Due: ${task.dueDate}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TealPrimary)
                            }
                        }
                    }
                }
            }
        }
    }

    if (isCreateDialogOpen) {
        AlertDialog(
            onDismissRequest = { isCreateDialogOpen = false },
            title = { Text("Delegate New Task", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("Task Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = taskDesc,
                        onValueChange = { taskDesc = it },
                        label = { Text("Instructions") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = assignToName,
                        onValueChange = { assignToName = it },
                        label = { Text("Assign To Employee Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("Due Date") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                OrcaButton(
                    text = "Assign Task",
                    onClick = {
                        scope.launch {
                            val manager = currentUser?.fullName ?: "HR Admin"
                            val managerId = currentUser?.id ?: "OL009"
                            val newTask = HrmsTask(
                                id = "TSK-${UUID.randomUUID().toString().take(6)}",
                                title = taskTitle,
                                description = taskDesc,
                                assignedToId = assignToId,
                                assignedToName = assignToName,
                                assignedById = managerId,
                                assignedByName = manager,
                                dueDate = dueDate,
                                priority = TaskPriority.HIGH,
                                status = TaskStatus.PENDING
                            )
                            taskRepository.createTask(newTask)
                            isCreateDialogOpen = false
                            taskTitle = ""
                            taskDesc = ""
                        }
                    },
                    enabled = taskTitle.isNotBlank()
                )
            },
            dismissButton = {
                Button(
                    onClick = { isCreateDialogOpen = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = SlateDark)
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
