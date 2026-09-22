package com.orcalabs.hrms.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.HrmsTask
import com.orcalabs.hrms.data.model.TaskPriority
import com.orcalabs.hrms.data.model.TaskStatus
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class TaskUiState(
    val tasks: List<HrmsTask> = emptyList(),
    val filteredTasks: List<HrmsTask> = emptyList(),
    val selectedFilter: String = "ALL", // "ALL", "PENDING", "IN_PROGRESS", "COMPLETED"
    val isCompleteDialogOpen: Boolean = false,
    val taskToComplete: HrmsTask? = null,
    val completionNotes: String = ""
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                val empId = user?.id ?: "OL010"
                loadTasks(empId)
            }
        }
    }

    private fun loadTasks(empId: String) {
        viewModelScope.launch {
            taskRepository.getTasksForUser(empId).collect { list ->
                _uiState.value = _uiState.value.copy(
                    tasks = list,
                    filteredTasks = applyFilter(list, _uiState.value.selectedFilter)
                )
            }
        }
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter,
            filteredTasks = applyFilter(_uiState.value.tasks, filter)
        )
    }

    private fun applyFilter(tasks: List<HrmsTask>, filter: String): List<HrmsTask> {
        return when (filter) {
            "PENDING" -> tasks.filter { it.status == TaskStatus.PENDING }
            "IN_PROGRESS" -> tasks.filter { it.status == TaskStatus.IN_PROGRESS }
            "COMPLETED" -> tasks.filter { it.status == TaskStatus.COMPLETED || it.status == TaskStatus.APPROVED }
            else -> tasks
        }
    }

    fun openCompleteDialog(task: HrmsTask) {
        _uiState.value = _uiState.value.copy(
            isCompleteDialogOpen = true,
            taskToComplete = task,
            completionNotes = ""
        )
    }

    fun closeCompleteDialog() {
        _uiState.value = _uiState.value.copy(isCompleteDialogOpen = false, taskToComplete = null)
    }

    fun onNotesChange(notes: String) {
        _uiState.value = _uiState.value.copy(completionNotes = notes)
    }

    fun markTaskCompleted() {
        val task = _uiState.value.taskToComplete ?: return
        viewModelScope.launch {
            taskRepository.updateTaskStatus(task.id, TaskStatus.COMPLETED, _uiState.value.completionNotes)
            closeCompleteDialog()
        }
    }
}
