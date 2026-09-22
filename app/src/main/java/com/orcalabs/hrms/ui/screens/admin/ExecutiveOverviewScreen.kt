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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.MetricCard
import com.orcalabs.hrms.ui.common.SectionHeader
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun ExecutiveOverviewScreen(
    viewModel: ExecutiveViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val data = uiState.data

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Executive Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = SurfaceWhite,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Executive Performance Overview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = SlateDark
                    )
                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = TealPrimary)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Q3 Target Achievement & Pharma Field Operations",
                    fontSize = 12.sp,
                    color = SlateTextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar for Monthly Target
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Monthly Target Progress", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                    Text("${data.monthlyPobTargetAchievedPct}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TealPrimary)
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = (data.monthlyPobTargetAchievedPct / 100).toFloat(),
                    color = TealPrimary,
                    trackColor = TealSoft,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Ratios
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricCard(
                title = "Doctor Coverage",
                value = "${uiState.doctorCoveragePct}%",
                subtitle = "Class A+ & A Doctors",
                icon = Icons.Default.MedicalServices,
                accentColor = TealPrimary,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Chemist Coverage",
                value = "${uiState.chemistCoveragePct}%",
                subtitle = "Retail Drugstores",
                icon = Icons.Default.Assessment,
                accentColor = IndigoAccent,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricCard(
                title = "Daily Call Avg",
                value = "${uiState.avgCallsPerRep}",
                subtitle = "Calls / Representative",
                icon = Icons.Default.TrendingUp,
                accentColor = Color(0xFF0EA5E9),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "POB Today",
                value = "₹3.48L",
                subtitle = "Across 3 Territories",
                icon = Icons.Default.CurrencyRupee,
                accentColor = Color(0xFF10B981),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Top Performer Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TealContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Top Performing Executive This Month", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                    Text(uiState.topPerformingRep, fontSize = 12.sp, color = TealPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Zonal Target vs Achievement")

        data.zones.forEach { zone ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(zone.zoneName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    val pct = ((zone.totalPobAmountInr / zone.targetPobInr) * 100).toInt()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Achievement: $pct%", fontSize = 11.sp, color = SlateTextSecondary)
                        Text("₹${zone.totalPobAmountInr.toInt()} of ₹${zone.targetPobInr.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TealPrimary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = (pct / 100f).coerceIn(0f, 1f),
                        color = if (pct >= 85) StatusPresent else Color(0xFFF59E0B),
                        trackColor = Color(0xFFF1F5F9),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                    )
                }
            }
        }
    }
}
