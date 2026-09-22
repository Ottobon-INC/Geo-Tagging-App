package com.orcalabs.hrms.data.model

enum class TaskStatus(val label: String) {
    PENDING("To Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    SUBMITTED_FOR_REVIEW("Submitted for Review"),
    APPROVED("Approved"),
    OVERDUE("Overdue")
}

enum class TaskPriority(val label: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent")
}

data class HrmsTask(
    val id: String,
    val title: String,
    val description: String,
    val assignedToId: String,
    val assignedToName: String,
    val assignedById: String,
    val assignedByName: String,
    val dueDate: String,               // "2026-09-30"
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING,
    val createdAt: String = "2026-09-20",
    val completedAt: String? = null,
    val submissionNotes: String? = null
)
