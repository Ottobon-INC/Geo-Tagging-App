package com.orcalabs.hrms.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

data class FieldRepPin(
    val id: String,
    val repName: String,
    val role: String,
    val territory: String,
    val currentVisit: String,
    val completedCalls: Int,
    val targetCalls: Int,
    val pobToday: Double,
    val lastPing: String,
    val batteryPct: Int
)

@Composable
fun FieldOpsMapScreen(
    modifier: Modifier = Modifier
) {
    val samplePins = remember {
        listOf(
            FieldRepPin("OL010", "Sandeep Reddy G", "BE", "Hyderabad - Banjara Hills", "Apollo Clinics, Rd 12", 3, 8, 28500.0, "2 mins ago", 88),
            FieldRepPin("OL011", "Mahesh Kumar P", "BE", "Secunderabad - Marredpally", "Shenoy Hospitals", 4, 8, 15000.0, "5 mins ago", 74),
            FieldRepPin("OL013", "A Naveen", "BE", "Warangal - Urban", "MGM Hospital Area", 2, 7, 12000.0, "12 mins ago", 91),
            FieldRepPin("OL016", "D Siva Kumar", "BE", "Vijayawada - Central", "Andhra Hospitals", 5, 9, 34000.0, "1 min ago", 82),
            FieldRepPin("OL018", "M Ravi Teja", "BE", "Visakhapatnam - City", "Seven Hills Hospital", 3, 8, 22000.0, "8 mins ago", 65),
            FieldRepPin("OL022", "C Mohan Krishna", "BE", "Tirupati - SVIMS Area", "SVIMS Outpatient Complex", 4, 8, 18500.0, "4 mins ago", 79)
        )
    }

    var selectedRep by remember { mutableStateOf(samplePins.first()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        // Map Radar Visual Frame
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SlateDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background grid / radar design
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(StatusPresent)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LIVE FIELD FORCE RADAR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        StatusBadge(
                            label = "21 Active On Field",
                            textColor = StatusPresent,
                            bgColor = Color(0xFF064E3B)
                        )
                    }

                    // Mock Rep Coordinates Visual Indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        samplePins.take(4).forEach { rep ->
                            val isSel = selectedRep.id == rep.id
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable { selectedRep = rep }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(if (isSel) 34.dp else 26.dp)
                                        .clip(CircleShape)
                                        .background(if (isSel) TealPrimary else Color(0xFF334155)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Navigation,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(if (isSel) 18.dp else 14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = rep.repName.split(" ")[0],
                                    fontSize = 10.sp,
                                    color = if (isSel) TealSoft else Color.White.copy(alpha = 0.7f),
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Bottom radar coordinates bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Active Territory: Telangana & Andhra Pradesh", fontSize = 10.sp, color = Color(0xFF94A3B8))
                        Text("GPS: Locked", fontSize = 10.sp, color = StatusPresent, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Selected Rep Detail Card
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = SurfaceWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(selectedRep.repName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SlateDark)
                        Text(selectedRep.territory, fontSize = 11.sp, color = SlateTextSecondary)
                    }
                    StatusBadge(
                        label = "Battery: ${selectedRep.batteryPct}%",
                        textColor = StatusPresent,
                        bgColor = Color(0xFFECFDF5)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Current Visit: ${selectedRep.currentVisit}",
                        fontSize = 12.sp,
                        color = SlateDark,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Calls: ${selectedRep.completedCalls} of ${selectedRep.targetCalls}", fontSize = 11.sp, color = SlateTextSecondary)
                    Text("POB: ₹${selectedRep.pobToday.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TealPrimary)
                    Text("Pinged: ${selectedRep.lastPing}", fontSize = 11.sp, color = SlateTextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "ALL FIELD EXECUTIVES (LIVE ROSTER)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(samplePins) { rep ->
                val isSelected = rep.id == selectedRep.id
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) TealContainer else SurfaceWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedRep = rep }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(rep.repName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateDark)
                            Text(rep.territory, fontSize = 11.sp, color = SlateTextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("${rep.completedCalls}/${rep.targetCalls} Calls", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TealPrimary)
                            Text("₹${rep.pobToday.toInt()} POB", fontSize = 10.sp, color = StatusPresent, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
