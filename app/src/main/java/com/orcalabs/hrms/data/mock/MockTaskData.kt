package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.HrmsTask
import com.orcalabs.hrms.data.model.TaskPriority
import com.orcalabs.hrms.data.model.TaskStatus

object MockTaskData {

    val initialTasks: MutableList<HrmsTask> = mutableListOf(
        HrmsTask(
            id = "TSK-001",
            title = "Submit Monthly Doctor Master List (October 2026)",
            description = "Update coverage list of Class A+ and Class A doctors with current clinic timings and phone numbers.",
            assignedToId = "OL010",
            assignedToName = "Sandeep Reddy G",
            assignedById = "OL005",
            assignedByName = "Murali Krishna K (RSM)",
            dueDate = "2026-09-25",
            priority = TaskPriority.HIGH,
            status = TaskStatus.IN_PROGRESS
        ),
        HrmsTask(
            id = "TSK-002",
            title = "Complete Cycle 3 Secondary Sales Reconciliation",
            description = "Collect closing stock statements from Apollo and Balaji Medical stores and match against dispatch notes.",
            assignedToId = "OL010",
            assignedToName = "Sandeep Reddy G",
            assignedById = "OL003",
            assignedByName = "Ramendra Kumar (ZSM)",
            dueDate = "2026-09-28",
            priority = TaskPriority.URGENT,
            status = TaskStatus.PENDING
        ),
        HrmsTask(
            id = "TSK-003",
            title = "Distribute Orca-D3 60K CME invitations",
            description = "Hand over invitations for upcoming Regional Orthopedic CME meeting to Dr. Srinivas Rao and Dr. Venkatesh.",
            assignedToId = "OL010",
            assignedToName = "Sandeep Reddy G",
            assignedById = "OL005",
            assignedByName = "Murali Krishna K (RSM)",
            dueDate = "2026-09-24",
            priority = TaskPriority.MEDIUM,
            status = TaskStatus.COMPLETED,
            completedAt = "2026-09-22 11:30 AM",
            submissionNotes = "Handed invitations personally to both doctors during morning clinic rounds."
        ),
        HrmsTask(
            id = "TSK-004",
            title = "Submit Q3 Field Allowance & Travel Expense Bills",
            description = "Upload fuel receipts and travel logs to the HR portal for monthly reimbursement.",
            assignedToId = "OL010",
            assignedToName = "Sandeep Reddy G",
            assignedById = "OL009",
            assignedByName = "N V Divya Sirisha (HR)",
            dueDate = "2026-09-30",
            priority = TaskPriority.MEDIUM,
            status = TaskStatus.PENDING
        ),
        HrmsTask(
            id = "TSK-005",
            title = "Verify Coastal AP Chemist KYC Documents",
            description = "Ensure drug license copies and GST certificates are updated for 14 new retail stockists in Vizag.",
            assignedToId = "OL018",
            assignedToName = "M Ravi Teja",
            assignedById = "OL007",
            assignedByName = "K Rajesh Varma (RSM)",
            dueDate = "2026-09-26",
            priority = TaskPriority.HIGH,
            status = TaskStatus.IN_PROGRESS
        )
    )
}
