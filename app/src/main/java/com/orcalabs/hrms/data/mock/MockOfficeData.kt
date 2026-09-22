package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.OfficeLocation

object MockOfficeData {

    val offices: MutableList<OfficeLocation> = mutableListOf(
        OfficeLocation(
            id = "OFF-001",
            name = "Orca Labs Corporate HQ",
            address = "Plot 42, Cyber Gateway, HITEC City, Hyderabad, Telangana 500081",
            latitude = 17.4435,
            longitude = 78.3772,
            radiusMeters = 200.0,
            isActive = true,
            totalEmployeesAssigned = 15
        ),
        OfficeLocation(
            id = "OFF-002",
            name = "Vijayawada Regional Office",
            address = "Door No. 40-1-65, MG Road, Labbipet, Vijayawada, Andhra Pradesh 520010",
            latitude = 16.5062,
            longitude = 80.6480,
            radiusMeters = 150.0,
            isActive = true,
            totalEmployeesAssigned = 10
        ),
        OfficeLocation(
            id = "OFF-003",
            name = "Visakhapatnam Operations Hub",
            address = "Dwaraka Nagar 3rd Lane, Visakhapatnam, Andhra Pradesh 530016",
            latitude = 17.7289,
            longitude = 83.3082,
            radiusMeters = 150.0,
            isActive = true,
            totalEmployeesAssigned = 8
        ),
        OfficeLocation(
            id = "OFF-004",
            name = "Warangal Area Depot",
            address = "Subedari Main Road, Hanamkonda, Warangal, Telangana 506001",
            latitude = 17.9784,
            longitude = 79.5941,
            radiusMeters = 100.0,
            isActive = true,
            totalEmployeesAssigned = 5
        )
    )
}
