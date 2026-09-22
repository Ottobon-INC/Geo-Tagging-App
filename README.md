# Orca HRMS - Native Android Application (Kotlin + Jetpack Compose)

Enterprise Pharmaceutical Field Force & Workforce Management System.

---

## Architecture Overview

- **Language & UI Toolkit:** 100% Kotlin with Jetpack Compose & Material 3
- **Design Theme:** Orca Labs Brand (Teal `#0D9488` with crisp white & slate surfaces)
- **Architecture:** Clean Architecture + MVVM + Repository Pattern
- **Dependency Injection:** Dagger Hilt
- **Navigation:** Jetpack Navigation Compose with role-based routing (Field Rep vs Manager vs Executive/Admin)
- **Maps & Geolocation:** OpenStreetMap / OSMDroid (zero Google Maps API key required)
- **Phase 1 Backend Strategy:** In-Memory Mock Repositories (`MockAuthRepository`, `MockAttendanceRepository`, etc.) populated with the **real 35 Orca Labs employees (OL009–OL045)** found in the database.
- **Phase 2 Switch Point:** [`AppModule.kt`](file:///c:/Users/HP/OneDrive/Desktop/Geo-Tagging/OrcaHRMS/app/src/main/java/com/orcalabs/hrms/di/AppModule.kt). To switch from Phase 1 mock data to live Supabase backend, only swap the 8 interface bindings in `AppModule.kt`. Zero changes required in any UI screen, ViewModel, or navigation graph.

---

## Built-in Test Credentials

All accounts can be tested directly from the login screen using the **Quick Test Role Chips** or by entering:

| Role | Name | Email | Password |
|---|---|---|---|
| **BE (Field Rep)** | Sandeep Reddy G (OL010) | `sandeep.reddy@orcalabs.in` | `sandeep.reddy@orcalabs.in` |
| **RSM (Regional Manager)** | Murali Krishna K (OL005) | `murali.krishna@orcalabs.in` | `murali.krishna@orcalabs.in` |
| **ZSM (Zonal Manager)** | Ramendra Kumar (OL003) | `ramendra.kumar@orcalabs.in` | `ramendra.kumar@orcalabs.in` |
| **HR Manager** | N V Divya Sirisha (OL009) | `divya.sirisha@orcalabs.in` | `divya.sirisha@orcalabs.in` |
| **General Manager** | P Aswani (OL026) | `aswani.p@orcalabs.in` | `aswani.p@orcalabs.in` |

*Note: In the app's navigation drawer, you can also use the **"Switch Test Role"** instant selector to test any role with a single tap without logging out.*

---

## 23 Screens Implemented

1. **Authentication:**
   - `LoginScreen`: Branded authentication screen with instant quick-test role buttons.

2. **Personal & Field Force Operations:**
   - `DashboardScreen`: Welcome card, live punch banner, daily KPI cards, doctor appointment shortcut, quick actions.
   - `PunchScreen`: Live digital clock, 200m geofence verification badge, selfie face-verification viewfinder simulator, circular punch button, today's punch timeline.
   - `AttendanceHistoryScreen`: Monthly summary pills (Present, Field Duty, Half Day, Leaves) with 30-day logs and status filters.
   - `LeaveScreen`: Leave balance cards (Casual, Sick, Earned), floating action button to apply for leave with dialog, history list with approval status and reviewer remarks.
   - `FieldDutyScreen`: Field work session start/stop controller, active tracking badge, today's completed calls log, POB revenue counter.
   - `CallCaptureScreen`: Pharma doctor and chemist call reporting with product detailing checklist (Orca-Cef, Amoxi-Orca, etc.), sample distribution counters, POB value in ₹, and feedback notes.
   - `DoctorPlannerScreen`: Monthly and daily tour planner with Class A+, A, and B priority doctor coverage and instant "Call" button.
   - `TaskListScreen`: Tasks delegated by managers with priority badges, status filters, and "Mark Completed" dialog with submission notes.
   - `ChatScreen`: Team channels ("News & Announcements", "Telangana Sales", "Andhra Pradesh Sales") with real-time reactive message bubbles.
   - `ProfileScreen`: Detailed employee profile, department, territory, reporting manager, and sign out button.

3. **Management & Executive Governance:**
   - `AdminDashboardScreen`: Operations portal with 6 enterprise KPI cards, administrative module shortcuts, and territory target vs achievement summary.
   - `EmployeeDirectoryScreen`: Searchable directory of all 35 staff with designation, HQ, reporting structure, and real-time attendance status.
   - `AdminAttendanceScreen`: Real-time staff roll-call showing who is currently punched in, on tour, or absent.
   - `LeaveApprovalScreen`: Pending employee leave inbox with one-tap "Approve" and "Reject" workflows.
   - `AdminTaskManagerScreen`: Manager task delegation portal to assign field duties and DCR deadlines.
   - `OfficeLocationsScreen`: Geofenced corporate and regional offices (Hyderabad HQ, Vijayawada, Vizag, Warangal) with coordinates and radius.
   - `FieldOpsMapScreen`: Live field force radar showing real-time representative locations, battery status, and calls completed across Telangana & AP.
   - `ExecutiveOverviewScreen`: C-level analytics with monthly target progress bar (84.5%), doctor coverage %, chemist coverage %, and zonal performance breakdown.
   - `OrgHierarchyScreen`: Interactive visual tree of the pharma hierarchy: GM (P Aswani) → ZSMs (Ramendra Kumar, B V Janardhan) → RSMs → Business Executives (BE).
   - `AdminSettingsScreen`: Enterprise configurations, strict geofence toggle, selfie verification toggle, offline sync, and Supabase multi-tenant readiness status.

---

## How to Open and Run in Android Studio

1. Open **Android Studio**.
2. Select **Open** and choose the folder:
   `c:\Users\HP\OneDrive\Desktop\Geo-Tagging\OrcaHRMS`
3. Android Studio will automatically recognize the Gradle project and use its bundled JDK 25/17.
4. Click **Run 'app'** or press `Shift + F10` to deploy to an emulator or physical Android device.
