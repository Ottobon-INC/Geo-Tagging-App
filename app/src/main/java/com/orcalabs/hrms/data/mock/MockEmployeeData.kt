package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.data.model.UserRole

object MockEmployeeData {

    val employees: List<Employee> = listOf(
        // Executive & Management
        Employee(
            id = "OL026",
            fullName = "P Aswani",
            email = "aswani.p@orcalabs.in",
            phone = "+91 98490 11026",
            role = UserRole.GM,
            designation = "General Manager - Operations",
            department = "Executive Management",
            zone = "Corporate",
            headquarter = "Hyderabad HQ",
            isPunchInToday = true,
            lastPunchTime = "09:05 AM"
        ),
        Employee(
            id = "OL009",
            fullName = "N V Divya Sirisha",
            email = "divya.sirisha@orcalabs.in",
            phone = "+91 98490 11009",
            role = UserRole.HR,
            designation = "Senior HR Manager",
            department = "Human Resources",
            zone = "Corporate",
            headquarter = "Hyderabad HQ",
            reportsToId = "OL026",
            reportsToName = "P Aswani",
            isPunchInToday = true,
            lastPunchTime = "09:12 AM"
        ),

        // Zonal Sales Managers (ZSMs)
        Employee(
            id = "OL003",
            fullName = "Ramendra Kumar",
            email = "ramendra.kumar@orcalabs.in",
            phone = "+91 98490 11003",
            role = UserRole.ZSM,
            designation = "Zonal Sales Manager - Telangana",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Hyderabad",
            reportsToId = "OL026",
            reportsToName = "P Aswani",
            isPunchInToday = true,
            lastPunchTime = "08:50 AM"
        ),
        Employee(
            id = "OL004",
            fullName = "B V Janardhan",
            email = "janardhan.bv@orcalabs.in",
            phone = "+91 98490 11004",
            role = UserRole.ZSM,
            designation = "Zonal Sales Manager - Andhra Pradesh",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Vijayawada",
            reportsToId = "OL026",
            reportsToName = "P Aswani",
            isPunchInToday = true,
            lastPunchTime = "09:18 AM"
        ),

        // Regional Sales Managers (RSMs)
        Employee(
            id = "OL005",
            fullName = "Murali Krishna K",
            email = "murali.krishna@orcalabs.in",
            phone = "+91 98490 11005",
            role = UserRole.RSM,
            designation = "Regional Sales Manager - Hyderabad North",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Secunderabad",
            reportsToId = "OL003",
            reportsToName = "Ramendra Kumar",
            isPunchInToday = true,
            lastPunchTime = "09:25 AM"
        ),
        Employee(
            id = "OL006",
            fullName = "Ch Suresh Babu",
            email = "suresh.babu@orcalabs.in",
            phone = "+91 98490 11006",
            role = UserRole.RSM,
            designation = "Regional Sales Manager - Telangana Rural",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Warangal",
            reportsToId = "OL003",
            reportsToName = "Ramendra Kumar",
            isPunchInToday = false
        ),
        Employee(
            id = "OL007",
            fullName = "K Rajesh Varma",
            email = "rajesh.varma@orcalabs.in",
            phone = "+91 98490 11007",
            role = UserRole.RSM,
            designation = "Regional Sales Manager - Coastal AP",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Visakhapatnam",
            reportsToId = "OL004",
            reportsToName = "B V Janardhan",
            isPunchInToday = true,
            lastPunchTime = "09:00 AM"
        ),
        Employee(
            id = "OL008",
            fullName = "T Venkatesh Rao",
            email = "venkatesh.rao@orcalabs.in",
            phone = "+91 98490 11008",
            role = UserRole.RSM,
            designation = "Regional Sales Manager - Rayalaseema",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Tirupati",
            reportsToId = "OL004",
            reportsToName = "B V Janardhan",
            isPunchInToday = true,
            lastPunchTime = "09:10 AM"
        ),

        // Business Executives (BEs) - Field Force
        Employee(
            id = "OL010",
            fullName = "Sandeep Reddy G",
            email = "sandeep.reddy@orcalabs.in",
            phone = "+91 98490 11010",
            role = UserRole.BE,
            designation = "Business Executive - Banjara Hills",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Hyderabad",
            reportsToId = "OL005",
            reportsToName = "Murali Krishna K",
            isPunchInToday = true,
            lastPunchTime = "09:30 AM"
        ),
        Employee(
            id = "OL011",
            fullName = "Mahesh Kumar P",
            email = "mahesh.kumar@orcalabs.in",
            phone = "+91 98490 11011",
            role = UserRole.BE,
            designation = "Business Executive - Secunderabad",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Secunderabad",
            reportsToId = "OL005",
            reportsToName = "Murali Krishna K",
            isPunchInToday = true,
            lastPunchTime = "09:40 AM"
        ),
        Employee(
            id = "OL012",
            fullName = "Kiranmai V",
            email = "kiranmai.v@orcalabs.in",
            phone = "+91 98490 11012",
            role = UserRole.BE,
            designation = "Business Executive - Dilsukhnagar",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Hyderabad",
            reportsToId = "OL005",
            reportsToName = "Murali Krishna K",
            isPunchInToday = false
        ),
        Employee(
            id = "OL013",
            fullName = "A Naveen",
            email = "naveen.a@orcalabs.in",
            phone = "+91 98490 11013",
            role = UserRole.BE,
            designation = "Business Executive - Warangal Urban",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Warangal",
            reportsToId = "OL006",
            reportsToName = "Ch Suresh Babu",
            isPunchInToday = true,
            lastPunchTime = "09:15 AM"
        ),
        Employee(
            id = "OL014",
            fullName = "Prashanth M",
            email = "prashanth.m@orcalabs.in",
            phone = "+91 98490 11014",
            role = UserRole.BE,
            designation = "Business Executive - Karimnagar",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Karimnagar",
            reportsToId = "OL006",
            reportsToName = "Ch Suresh Babu",
            isPunchInToday = true,
            lastPunchTime = "09:05 AM"
        ),
        Employee(
            id = "OL015",
            fullName = "Venkata Satish R",
            email = "satish.r@orcalabs.in",
            phone = "+91 98490 11015",
            role = UserRole.BE,
            designation = "Business Executive - Khammam",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Khammam",
            reportsToId = "OL006",
            reportsToName = "Ch Suresh Babu",
            isPunchInToday = true,
            lastPunchTime = "09:20 AM"
        ),
        Employee(
            id = "OL016",
            fullName = "D Siva Kumar",
            email = "siva.kumar@orcalabs.in",
            phone = "+91 98490 11016",
            role = UserRole.BE,
            designation = "Business Executive - Vijayawada Central",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Vijayawada",
            reportsToId = "OL007",
            reportsToName = "K Rajesh Varma",
            isPunchInToday = true,
            lastPunchTime = "09:10 AM"
        ),
        Employee(
            id = "OL017",
            fullName = "B Harish",
            email = "harish.b@orcalabs.in",
            phone = "+91 98490 11017",
            role = UserRole.BE,
            designation = "Business Executive - Guntur",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Guntur",
            reportsToId = "OL007",
            reportsToName = "K Rajesh Varma",
            isPunchInToday = true,
            lastPunchTime = "09:15 AM"
        ),
        Employee(
            id = "OL018",
            fullName = "M Ravi Teja",
            email = "ravi.teja@orcalabs.in",
            phone = "+91 98490 11018",
            role = UserRole.BE,
            designation = "Business Executive - Visakhapatnam City",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Visakhapatnam",
            reportsToId = "OL007",
            reportsToName = "K Rajesh Varma",
            isPunchInToday = true,
            lastPunchTime = "09:00 AM"
        ),
        Employee(
            id = "OL019",
            fullName = "K Srimannarayana",
            email = "sriman.k@orcalabs.in",
            phone = "+91 98490 11019",
            role = UserRole.BE,
            designation = "Business Executive - Rajahmundry",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Rajahmundry",
            reportsToId = "OL007",
            reportsToName = "K Rajesh Varma",
            isPunchInToday = false
        ),
        Employee(
            id = "OL020",
            fullName = "G Anil Kumar",
            email = "anil.kumar@orcalabs.in",
            phone = "+91 98490 11020",
            role = UserRole.BE,
            designation = "Business Executive - Kurnool",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Kurnool",
            reportsToId = "OL008",
            reportsToName = "T Venkatesh Rao",
            isPunchInToday = true,
            lastPunchTime = "08:55 AM"
        ),
        Employee(
            id = "OL021",
            fullName = "N Bhanu Prasad",
            email = "bhanu.prasad@orcalabs.in",
            phone = "+91 98490 11021",
            role = UserRole.BE,
            designation = "Business Executive - Anantapur",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Anantapur",
            reportsToId = "OL008",
            reportsToName = "T Venkatesh Rao",
            isPunchInToday = true,
            lastPunchTime = "09:20 AM"
        ),
        Employee(
            id = "OL022",
            fullName = "C Mohan Krishna",
            email = "mohan.krishna@orcalabs.in",
            phone = "+91 98490 11022",
            role = UserRole.BE,
            designation = "Business Executive - Tirupati",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Tirupati",
            reportsToId = "OL008",
            reportsToName = "T Venkatesh Rao",
            isPunchInToday = true,
            lastPunchTime = "09:30 AM"
        ),
        Employee(
            id = "OL023",
            fullName = "K Vinod",
            email = "vinod.k@orcalabs.in",
            phone = "+91 98490 11023",
            role = UserRole.BE,
            designation = "Business Executive - Nellore",
            department = "Sales & Marketing",
            zone = "Andhra Pradesh",
            headquarter = "Nellore",
            reportsToId = "OL008",
            reportsToName = "T Venkatesh Rao",
            isPunchInToday = true,
            lastPunchTime = "09:12 AM"
        ),
        Employee(
            id = "OL024",
            fullName = "P Sai Chaitanya",
            email = "sai.chaitanya@orcalabs.in",
            phone = "+91 98490 11024",
            role = UserRole.BE,
            designation = "Business Executive - Kukatpally",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Hyderabad",
            reportsToId = "OL005",
            reportsToName = "Murali Krishna K",
            isPunchInToday = true,
            lastPunchTime = "09:25 AM"
        ),
        Employee(
            id = "OL025",
            fullName = "Y Raghavendra",
            email = "raghavendra.y@orcalabs.in",
            phone = "+91 98490 11025",
            role = UserRole.BE,
            designation = "Business Executive - Nizamabad",
            department = "Sales & Marketing",
            zone = "Telangana",
            headquarter = "Nizamabad",
            reportsToId = "OL006",
            reportsToName = "Ch Suresh Babu",
            isPunchInToday = false
        )
    )

    fun findByEmail(email: String): Employee? {
        val clean = email.trim().lowercase()
        return employees.firstOrNull { it.email.lowercase() == clean }
    }

    fun findById(id: String): Employee? {
        return employees.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    // Default logged-in user for fast testing if needed
    val defaultFieldUser: Employee = employees.first { it.id == "OL010" } // Sandeep Reddy (BE)
    val defaultAdminUser: Employee = employees.first { it.id == "OL009" } // Divya Sirisha (HR)
    val defaultZsmUser: Employee = employees.first { it.id == "OL003" }   // Ramendra Kumar (ZSM)
}
