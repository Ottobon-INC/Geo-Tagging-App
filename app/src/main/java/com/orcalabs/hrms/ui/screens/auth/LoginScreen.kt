package com.orcalabs.hrms.ui.screens.auth

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.ui.common.OrcaButton
import com.orcalabs.hrms.ui.theme.IndigoAccent
import com.orcalabs.hrms.ui.theme.IndigoSoft
import com.orcalabs.hrms.ui.theme.SlateBorder
import com.orcalabs.hrms.ui.theme.SlateDark
import com.orcalabs.hrms.ui.theme.SlateTextSecondary
import com.orcalabs.hrms.ui.theme.TealPrimary
import com.orcalabs.hrms.ui.theme.TealSoft

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (Employee) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Branding Logo
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(TealPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = "Orca Labs Logo",
                tint = Color.White,
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Orca HRMS",
            style = MaterialTheme.typography.headlineLarge,
            color = SlateDark,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Pharmaceutical Field Force & Workforce Management",
            style = MaterialTheme.typography.bodyMedium,
            color = SlateTextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Quick Role Switcher Chips for Pair-Testing
        Text(
            text = "QUICK TEST LOGIN CHIPS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SlateTextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                Pair("BE (Field)", "sandeep.reddy@orcalabs.in"),
                Pair("RSM (Mgr)", "murali.krishna@orcalabs.in"),
                Pair("ZSM (Zone)", "ramendra.kumar@orcalabs.in"),
                Pair("HR", "divya.sirisha@orcalabs.in"),
                Pair("GM", "aswani.p@orcalabs.in")
            ).forEach { (label, email) ->
                val isCurrent = uiState.email == email
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isCurrent) TealSoft else Color.White,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isCurrent) TealPrimary else SlateBorder
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.fillQuickUser(email) }
                ) {
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCurrent) TealPrimary else SlateDark,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Email field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Corporate Email") },
            placeholder = { Text("e.g. name@orcalabs.in") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TealPrimary) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TealPrimary,
                unfocusedBorderColor = SlateBorder
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Password field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TealPrimary) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle Password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { viewModel.login(onLoginSuccess) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TealPrimary,
                unfocusedBorderColor = SlateBorder
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uiState.errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        if (uiState.isLoading) {
            CircularProgressIndicator(color = TealPrimary)
        } else {
            OrcaButton(
                text = "Sign In to Orca HRMS",
                onClick = { viewModel.login(onLoginSuccess) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "GPS Geofencing Active • Secured with Orca Guard",
            fontSize = 12.sp,
            color = SlateTextSecondary
        )
    }
}
