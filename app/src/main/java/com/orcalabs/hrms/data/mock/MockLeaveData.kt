package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.LeaveBalanceSummary
import com.orcalabs.hrms.data.model.LeaveRequest
import com.orcalabs.hrms.data.model.LeaveStatus
import com.orcalabs.hrms.data.model.LeaveType

object MockLeaveData {

    val initialLeaveRequests: MutableList<LeaveRequest> = mutableListOf(
        LeaveRequest(
            id = "LV-2026-001",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            leaveType = LeaveType.CASUAL,
            startDate = "2026-09-28",
            endDate = "2026-09-29",
            numberOfDays = 2.0,
            reason = "Family function in native place (Nalgonda)",
            status = LeaveStatus.PENDING,
            appliedOn = "2026-09-21"
        ),
        LeaveRequest(
            id = "LV-2026-002",
            employeeId = "OL012",
            employeeName = "Kiranmai V",
            leaveType = LeaveType.SICK,
            startDate = "2026-09-22",
            endDate = "2026-09-23",
            numberOfDays = 2.0,
            reason = "Viral fever and doctor advised 2 days rest",
            status = LeaveStatus.PENDING,
            appliedOn = "2026-09-21"
        ),
        LeaveRequest(
            id = "LV-2026-003",
            employeeId = "OL019",
            employeeName = "K Srimannarayana",
            leaveType = LeaveType.CASUAL,
            startDate = "2026-09-22",
            endDate = "2026-09-22",
            numberOfDays = 1.0,
            reason = "Personal work at bank",
            status = LeaveStatus.PENDING,
            appliedOn = "2026-09-20"
        ),
        LeaveRequest(
            id = "LV-2026-004",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            leaveType = LeaveType.SICK,
            startDate = "2026-08-12",
            endDate = "2026-08-13",
            numberOfDays = 2.0,
            reason = "Severe throat infection",
            status = LeaveStatus.APPROVED,
            appliedOn = "2026-08-11",
            reviewedBy = "N V Divya Sirisha (HR)",
            reviewedOn = "2026-08-11",
            reviewRemarks = "Approved, get well soon."
        ),
        LeaveRequest(
            id = "LV-2026-005",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            leaveType = LeaveType.CASUAL,
            startDate = "2026-07-04",
            endDate = "2026-07-04",
            numberOfDays = 1.0,
            reason = "Vehicle registration renewal",
            status = LeaveStatus.APPROVED,
            appliedOn = "2026-07-01",
            reviewedBy = "Murali Krishna K (RSM)",
            reviewedOn = "2026-07-02",
            reviewRemarks = "Approved"
        ),
        LeaveRequest(
            id = "LV-2026-006",
            employeeId = "OL025",
            employeeName = "Y Raghavendra",
            leaveType = LeaveType.CASUAL,
            startDate = "2026-09-22",
            endDate = "2026-09-24",
            numberOfDays = 3.0,
            reason = "Urgent family property matter",
            status = LeaveStatus.PENDING,
            appliedOn = "2026-09-21"
        )
    )

    val defaultBalances: MutableMap<String, LeaveBalanceSummary> = mutableMapOf(
        "OL010" to LeaveBalanceSummary(casualTotal = 12, casualUsed = 3, sickTotal = 10, sickUsed = 2, earnedTotal = 15, earnedUsed = 2),
        "OL009" to LeaveBalanceSummary(casualTotal = 12, casualUsed = 1, sickTotal = 10, sickUsed = 0, earnedTotal = 15, earnedUsed = 0),
        "OL003" to LeaveBalanceSummary(casualTotal = 12, casualUsed = 2, sickTotal = 10, sickUsed = 1, earnedTotal = 15, earnedUsed = 3)
    )
}
