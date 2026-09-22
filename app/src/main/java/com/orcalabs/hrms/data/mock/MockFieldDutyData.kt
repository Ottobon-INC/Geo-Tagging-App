package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.DoctorPlanItem
import com.orcalabs.hrms.data.model.FieldDutySession
import com.orcalabs.hrms.data.model.FieldVisit
import com.orcalabs.hrms.data.model.GeoPoint
import com.orcalabs.hrms.data.model.ProductPromotion
import com.orcalabs.hrms.data.model.VisitType

object MockFieldDutyData {

    val pharmaProducts: List<String> = listOf(
        "Orca-Cef 200 (Cefixime 200mg)",
        "Amoxi-Orca CV 625 (Amoxicillin + Clavulanic)",
        "Pan-Orca DSR (Pantoprazole + Domperidone)",
        "Orca-Cold Plus (Paracetamol + Phenylephrine)",
        "Orca-D3 60K (Cholecalciferol Capsules)",
        "Orca-Mont LC (Montelukast + Levocetirizine)",
        "Orca-Glyptin M (Teneligliptin + Metformin)",
        "Orca-Ator 10 (Atorvastatin 10mg)"
    )

    val plannedDoctors: MutableList<DoctorPlanItem> = mutableListOf(
        DoctorPlanItem(
            doctorId = "DOC-001",
            doctorName = "Dr. K. Srinivas Rao",
            specialty = "Consultant Physician",
            hospitalName = "Apollo Clinics",
            area = "Banjara Hills Rd 12",
            priority = "A+",
            isVisitedToday = true
        ),
        DoctorPlanItem(
            doctorId = "DOC-002",
            doctorName = "Dr. P. Madhavi Latha",
            specialty = "Gynecologist & Obstetrician",
            hospitalName = "Rainbow Hospitals",
            area = "Banjara Hills Rd 2",
            priority = "A+",
            isVisitedToday = true
        ),
        DoctorPlanItem(
            doctorId = "DOC-003",
            doctorName = "Dr. Ramesh Chandra",
            specialty = "Pediatrician",
            hospitalName = "Lotus Children Hospital",
            area = "Lakdikapul",
            priority = "A",
            isVisitedToday = false
        ),
        DoctorPlanItem(
            doctorId = "DOC-004",
            doctorName = "Dr. Syed Abdul Qadir",
            specialty = "General Physician & Diabetologist",
            hospitalName = "Medwin Hospital",
            area = "Nampally",
            priority = "A",
            isVisitedToday = false
        ),
        DoctorPlanItem(
            doctorId = "DOC-005",
            doctorName = "Dr. V. Sunitha",
            specialty = "ENT Specialist",
            hospitalName = "Care Hospital",
            area = "Banjara Hills",
            priority = "B",
            isVisitedToday = false
        ),
        DoctorPlanItem(
            doctorId = "DOC-006",
            doctorName = "Dr. C. Venkatesh",
            specialty = "Orthopedic Surgeon",
            hospitalName = "Sunshine Hospitals",
            area = "Secunderabad",
            priority = "A",
            isVisitedToday = false
        )
    )

    val completedVisitsToday: MutableList<FieldVisit> = mutableListOf(
        FieldVisit(
            id = "VISIT-001",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            visitType = VisitType.DOCTOR,
            contactPerson = "Dr. K. Srinivas Rao, MD",
            clinicOrShopName = "Apollo Clinics",
            location = GeoPoint(17.4156, 78.4750, 4.0f, "Banjara Hills Rd 12, Hyderabad"),
            timestamp = "10:15 AM",
            productsPromoted = listOf(
                ProductPromotion("P1", "Orca-Cef 200", isDetailed = true, samplesGiven = 4, promoGiftsGiven = 1),
                ProductPromotion("P3", "Pan-Orca DSR", isDetailed = true, samplesGiven = 2, promoGiftsGiven = 0)
            ),
            pobValueInr = 0.0,
            doctorFeedback = "Prescribed Orca-Cef in 8 respiratory infection patients last week. Very positive response.",
            nextVisitDate = "2026-10-06"
        ),
        FieldVisit(
            id = "VISIT-002",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            visitType = VisitType.CHEMIST,
            contactPerson = "Ramesh Kumar (Chemist)",
            clinicOrShopName = "Balaji Medical & General Stores",
            location = GeoPoint(17.4160, 78.4755, 5.0f, "Near Apollo Clinics, Rd 12"),
            timestamp = "11:00 AM",
            productsPromoted = listOf(
                ProductPromotion("P1", "Orca-Cef 200", samplesGiven = 0),
                ProductPromotion("P3", "Pan-Orca DSR", samplesGiven = 0),
                ProductPromotion("P2", "Amoxi-Orca CV 625", samplesGiven = 0)
            ),
            pobValueInr = 28500.0,
            doctorFeedback = "Booked POB for 10 boxes Orca-Cef and 15 boxes Pan-Orca DSR. High prescription flow from Dr. Srinivas Rao."
        ),
        FieldVisit(
            id = "VISIT-003",
            employeeId = "OL010",
            employeeName = "Sandeep Reddy G",
            visitType = VisitType.DOCTOR,
            contactPerson = "Dr. P. Madhavi Latha, DGO",
            clinicOrShopName = "Rainbow Hospitals",
            location = GeoPoint(17.4200, 78.4480, 5.0f, "Banjara Hills Rd 2"),
            timestamp = "12:30 PM",
            productsPromoted = listOf(
                ProductPromotion("P5", "Orca-D3 60K", isDetailed = true, samplesGiven = 5, promoGiftsGiven = 1)
            ),
            pobValueInr = 0.0,
            doctorFeedback = "Discussed high maternal vitamin D deficiency. Agreed to start trial on antenatal patients."
        )
    )

    var currentSession: FieldDutySession? = FieldDutySession(
        id = "SESSION-20260922-010",
        employeeId = "OL010",
        employeeName = "Sandeep Reddy G",
        date = "2026-09-22",
        startTime = "09:45 AM",
        isSessionActive = true,
        totalVisitsPlanned = 8,
        visitsCompleted = 3,
        totalPobBooked = 28500.0,
        startLocation = GeoPoint(17.4156, 78.4750, 4.0f, "Banjara Hills Rd 12")
    )
}
