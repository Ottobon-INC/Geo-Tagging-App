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
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.ui.common.RoleBadge
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun OrgHierarchyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = SurfaceWhite,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pharma Organization Structure",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = SlateDark
                    )
                    Text(
                        text = "GM → ZSM → RSM → Business Executives (BE)",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
                Icon(Icons.Default.AccountTree, contentDescription = null, tint = TealPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Level 1: General Manager
        OrgNodeCard(
            name = "P Aswani (OL026)",
            title = "General Manager - Operations",
            department = "Corporate Executive",
            levelBadge = "GM (Level 4)",
            accentColor = Color(0xFF7C3AED),
            modifier = Modifier.fillMaxWidth()
        )

        ConnectorLine()

        // Level 1.5: HR Manager
        OrgNodeCard(
            name = "N V Divya Sirisha (OL009)",
            title = "Senior HR Manager",
            department = "Corporate Human Resources",
            levelBadge = "HR (Level 4)",
            accentColor = IndigoAccent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        ConnectorLine()

        // Level 2: ZSMs
        Text(
            text = "ZONAL SALES MANAGERS (ZSM)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextSecondary
        )
        Spacer(modifier = Modifier.height(6.dp))

        OrgNodeCard(
            name = "Ramendra Kumar (OL003)",
            title = "Zonal Sales Manager - Telangana Zone",
            department = "14 Field Force Staff • Hyderabad HQ",
            levelBadge = "ZSM (Level 3)",
            accentColor = TealPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        // Sub RSMs under Ramendra
        Column(modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 12.dp)) {
            SubNodeItem("Murali Krishna K (OL005) - RSM Hyderabad North", "4 Field Executives")
            SubNodeItem("Ch Suresh Babu (OL006) - RSM Telangana Rural", "4 Field Executives")
        }

        OrgNodeCard(
            name = "B V Janardhan (OL004)",
            title = "Zonal Sales Manager - Andhra Pradesh Zone",
            department = "17 Field Force Staff • Vijayawada HQ",
            levelBadge = "ZSM (Level 3)",
            accentColor = Color(0xFF0EA5E9),
            modifier = Modifier.fillMaxWidth()
        )

        // Sub RSMs under Janardhan
        Column(modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 12.dp)) {
            SubNodeItem("K Rajesh Varma (OL007) - RSM Coastal AP (Vizag)", "4 Field Executives")
            SubNodeItem("T Venkatesh Rao (OL008) - RSM Rayalaseema (Tirupati)", "4 Field Executives")
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Level 4 Summary: Business Executives
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TealContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Groups, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "24 Business Executives (BE) in Field Force",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = SlateDark
                    )
                    Text(
                        text = "Covering 16 territories across Hyderabad, Warangal, Vizag, Vijayawada, Guntur, Tirupati & Kurnool.",
                        fontSize = 11.sp,
                        color = SlateTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun OrgNodeCard(
    name: String,
    title: String,
    department: String,
    levelBadge: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accentColor.copy(alpha = 0.12f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SlateDark)
                Text(title, fontSize = 11.sp, color = SlateTextSecondary)
                Text(department, fontSize = 10.sp, color = TealPrimary, fontWeight = FontWeight.Medium)
            }
            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = levelBadge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun ConnectorLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(16.dp)
                .background(SlateBorder)
        )
    }
}

@Composable
private fun SubNodeItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("↳ ", fontSize = 14.sp, color = TealPrimary, fontWeight = FontWeight.Bold)
        Column {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
            Text(subtitle, fontSize = 10.sp, color = SlateTextSecondary)
        }
    }
}
