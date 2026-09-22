package com.orcalabs.hrms.ui.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.domain.repository.AuthRepository
import com.orcalabs.hrms.ui.common.EmployeeAvatar
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary

@Composable
fun ProfileScreen(
    authRepository: AuthRepository,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by authRepository.currentUser.collectAsState()
    val user = currentUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Profile Banner Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmployeeAvatar(name = user?.fullName ?: "Orca Employee", sizeDp = 64)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = user?.fullName ?: "Employee Name",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SlateDark
                )
                Text(
                    text = user?.designation ?: "Business Executive",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                user?.let { RoleBadge(role = it.role) }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SectionHeader(title = "Employment & Territory Details")

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileFieldRow("Employee ID", user?.id ?: "OL010", Icons.Default.Person)
                ProfileFieldRow("Department", user?.department ?: "Sales & Marketing", Icons.Default.Business)
                ProfileFieldRow("Territory Zone", user?.zone ?: "Telangana", Icons.Default.LocationOn)
                ProfileFieldRow("Headquarter", user?.headquarter ?: "Hyderabad", Icons.Default.LocationOn)
                ProfileFieldRow("Reports To", user?.reportsToName ?: "Murali Krishna K (RSM)", Icons.Default.Person)
                ProfileFieldRow("Date of Joining", user?.joiningDate ?: "15 Jan 2023", Icons.Default.DateRange)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SectionHeader(title = "Contact Information")

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileFieldRow("Official Email", user?.email ?: "sandeep.reddy@orcalabs.in", Icons.Default.Email)
                ProfileFieldRow("Mobile Number", user?.phone ?: "+91 98490 11010", Icons.Default.Call)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Out Button
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF2F2), contentColor = Color(0xFFEF4444)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Sign Out", tint = Color(0xFFEF4444))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out of Account", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ProfileFieldRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, color = SlateTextSecondary)
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
        }
    }
}
