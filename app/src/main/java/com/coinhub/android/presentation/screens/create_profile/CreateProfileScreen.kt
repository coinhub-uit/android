package com.coinhub.android.presentation.screens.create_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.viewmodels.CreateProfileViewModel
import com.coinhub.android.utils.datePattern

@Composable
fun CreateProfileScreen(
    viewModel: CreateProfileViewModel = hiltViewModel(),
) {
    val fullNameState by viewModel.fullNameState.collectAsState()
    val birthDateState by viewModel.birthDateState.collectAsState()
    val citizenIdState by viewModel.citizenIdState.collectAsState()
    val addressState by viewModel.addressState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = fullNameState.fullName,
            onValueChange = { viewModel.onFullNameChanged(it) },
            label = { Text("Full Name") },
            isError = fullNameState.errorMessage != null,
            supportingText = { fullNameState.errorMessage?.let { Text(it) } },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = birthDateState.birthDateInString,
            onValueChange = { viewModel.onBirthDateChanged(it.toLongOrNull() ?: 0L) },
            label = { Text("Birth Date") },
            placeholder = { Text(datePattern) },
            isError = birthDateState.errorMessage != null,
            supportingText = { birthDateState.errorMessage?.let { Text(it) } },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = citizenIdState.citizenId,
            onValueChange = { viewModel.onCitizenIdChanged(it) },
            label = { Text("Citizen ID") },
            isError = citizenIdState.errorMessage != null,
            supportingText = { citizenIdState.errorMessage?.let { Text(it) } },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = addressState.value,
            onValueChange = { viewModel.onAddressChanged(it) },
            label = { Text("Address (Optional)") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
