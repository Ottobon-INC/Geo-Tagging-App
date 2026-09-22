package com.orcalabs.hrms.ui.screens.admin

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary

@Composable
fun AdminSettingsScreen(
    modifier: Modifier = Modifier
) {
    var geofenceStrict by remember { mutableStateOf(true) }
    var selfieVerification by remember { mutableStateOf(true) }
    var autoSyncOffline by remember { mutableStateOf(true) }
    var chemistPobApproval by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Multi-Tenant Phase 1 Status Banner
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TealContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudDone, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Phase 1: Zero-Key Mock Architecture",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = SlateDark
                        )
                        Text(
                            text = "Client: Orca Labs • Brand: Teal (#0D9488)",
                            fontSize = 11.sp,
                            color = TealPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Clean Architecture is fully active. Swapping in AppModule.kt to Supabase in Phase 2 requires ZERO changes to UI, ViewModels, or Navigation graphs.",
                    fontSize = 11.sp,
                    color = SlateTextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SectionHeader(title = "Attendance & Geofencing Rules")

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                SettingsToggleItem(
                    title = "Strict GPS Geofence Enforcement",
                    subtitle = "Block punch-in if executive is outside the 200m radius",
                    checked = geofenceStrict,
                    onCheckedChange = { geofenceStrict = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
                SettingsToggleItem(
                    title = "Selfie Face ID Verification",
                    subtitle = "Require front camera verification on attendance punch",
                    checked = selfieVerification,
                    onCheckedChange = { selfieVerification = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SectionHeader(title = "Field Force & Tour Plan Policies")

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                SettingsToggleItem(
                    title = "Offline Visit Caching & Auto-Sync",
                    subtitle = "Queue doctor & chemist calls when mobile data drops",
                    checked = autoSyncOffline,
                    onCheckedChange = { autoSyncOffline = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
                SettingsToggleItem(
                    title = "Manager POB Approval Workflow",
                    subtitle = "Require RSM approval for orders exceeding ₹50,000",
                    checked = chemistPobApproval,
                    onCheckedChange = { chemistPobApproval = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SectionHeader(title = "System Information")

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                InfoRow("App Version", "v1.0.0 (Production Release)")
                InfoRow("Package", "com.orcalabs.hrms")
                InfoRow("Mapping Engine", "OSMDroid (OpenStreetMap - Offline Ready)")
                InfoRow("Organization DB Target", "pharma_hrms_* (Orca Labs)")
                InfoRow("Security", "SHA-256 Auth & TLS 1.3 Pinning")
            }
        }
    }
}

@Composable
private fun SettingsToggleItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateDark)
            Text(subtitle, fontSize = 11.sp, color = SlateTextSecondary)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = TealPrimary)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 11.sp, color = SlateTextSecondary)
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
    }
}
