package com.coinhub.android.presentation.screens.auth

import androidx.compose.foundation.layout.Column
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
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel = hiltViewModel(),
) {
    val fullName by viewModel.fullName.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()
    val citizenId by viewModel.citizenId.collectAsState()
    val address by viewModel.address.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = fullName,
            onValueChange = { viewModel.setFullName(it) },
            label = { Text("Full Name") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = birthDate,
            onValueChange = { /* TODO:  */ },
            label = { Text("Birth Date") },
            placeholder = { Text(datePattern) },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = citizenId,
            onValueChange = { viewModel.setCitizenId(it) },
            label = { Text("Citizen ID") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = address,
            onValueChange = { viewModel.setAddress(it) },
            label = { Text("Address (Optional)") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
