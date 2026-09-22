package com.orcalabs.hrms.ui.screens.fieldduty

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.orcalabs.hrms.data.model.VisitType
import com.orcalabs.hrms.ui.common.OrcaButton
import com.orcalabs.hrms.ui.common.StatusBadge
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.StatusPresent
import com.orcalabs.hrms.ui.theme.SurfaceWhite
import com.orcalabs.hrms.ui.theme.TealContainer
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun CallCaptureScreen(
    viewModel: FieldDutyViewModel,
    onCallSaved: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val form = uiState.callForm

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Call Type Selector
        Text("CALL TYPE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateTextSecondary)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(VisitType.DOCTOR, VisitType.CHEMIST, VisitType.STOCKIST).forEach { type ->
                val isSelected = form.visitType == type
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) TealPrimary else SurfaceWhite,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TealPrimary else SlateBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.updateCallType(type) }
                ) {
                    Text(
                        text = type.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else SlateDark,
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Contact Person & Clinic/Store Name
        OutlinedTextField(
            value = form.contactPerson,
            onValueChange = { viewModel.updateContactPerson(it) },
            label = { Text(if (form.visitType == VisitType.DOCTOR) "Doctor Name & Qualification" else "Chemist / Contact Person") },
            leadingIcon = { Icon(Icons.Default.MedicalServices, contentDescription = null, tint = TealPrimary) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = form.clinicOrShopName,
            onValueChange = { viewModel.updateClinicName(it) },
            label = { Text("Hospital / Clinic / Pharmacy Name") },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = TealPrimary) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Geo-Location Tagging Badge
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = StatusPresent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Auto GPS Tagged: 17.4156° N, 78.4750° E (Banjara Hills Rd 12)",
                fontSize = 11.sp,
                color = SlateTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Detailing & Sample Distribution Section
        Text(
            text = "PRODUCTS DETAILED & SAMPLES GIVEN",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                uiState.productsList.forEach { product ->
                    val isChecked = form.selectedProducts.contains(product)
                    val samples = form.sampleQuantities[product] ?: 0

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f).clickable { viewModel.toggleProduct(product) }
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { viewModel.toggleProduct(product) },
                                colors = CheckboxDefaults.colors(checkedColor = TealPrimary)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = product,
                                fontSize = 12.sp,
                                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                                color = SlateDark
                            )
                        }

                        if (isChecked) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { viewModel.updateSampleQty(product, -1) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TealPrimary)
                                }
                                Text(
                                    text = "$samples",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateDark,
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                                IconButton(
                                    onClick = { viewModel.updateSampleQty(product, 1) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = TealPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // POB Booking
        OutlinedTextField(
            value = form.pobValue,
            onValueChange = { viewModel.updatePobValue(it) },
            label = { Text("Personal Order Booking (POB in ₹)") },
            leadingIcon = { Icon(Icons.Default.CurrencyRupee, contentDescription = null, tint = TealPrimary) },
            placeholder = { Text("0") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Doctor / Chemist Feedback
        OutlinedTextField(
            value = form.feedbackNotes,
            onValueChange = { viewModel.updateFeedbackNotes(it) },
            label = { Text("Discussion & Feedback Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (form.isSaving) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else {
            OrcaButton(
                text = "Save & Submit Visit Report",
                onClick = { viewModel.saveCall(onCallSaved) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
