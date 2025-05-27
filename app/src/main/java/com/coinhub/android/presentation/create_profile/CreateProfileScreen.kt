package com.coinhub.android.presentation.create_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.datePattern
import com.coinhub.android.utils.toDateString
import kotlinx.coroutines.launch

@Composable
fun CreateProfileScreen(
    onProfileCreated: () -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel(),
) {
    val fullName = viewModel.fullName.collectAsStateWithLifecycle().value
    val onFullNameChange = viewModel::onFullNameChange
    val fullNameCheckState = viewModel.fullNameCheckState.collectAsStateWithLifecycle().value
    val birthDateInMillis = viewModel.birthDateInMillis.collectAsStateWithLifecycle().value
    val onBirthDateInMillisChange = viewModel::onBirthDateInMillisChange
    val birthDateCheckState = viewModel.birthDateCheckState.collectAsStateWithLifecycle().value
    val citizenId = viewModel.citizenId.collectAsStateWithLifecycle().value
    val onCitizenIdChange = viewModel::onCitizenIdChange
    val citizenIdCheckState = viewModel.citizenIdCheckState.collectAsStateWithLifecycle().value
    val address = viewModel.address.collectAsStateWithLifecycle().value
    val onAddressChange = viewModel::onAddressChange
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    val onCreateProfile = viewModel::onCreateProfile

    val message = viewModel.message // WARN: Check this, will it be updated?

    CreateProfileScreen(
        fullName = fullName,
        onFullNameChange = onFullNameChange,
        fullNameCheckState = fullNameCheckState,
        birthDateInMillis = birthDateInMillis,
        onBirthDateInMillisChange = onBirthDateInMillisChange,
        birthDateCheckState = birthDateCheckState,
        citizenId = citizenId,
        onCitizenIdChange = onCitizenIdChange,
        citizenIdCheckState = citizenIdCheckState,
        address = address,
        onAddressChange = onAddressChange,
        isFormValid = isFormValid,
        message = message,
        onCreateProfile = onCreateProfile,
        onProfileCreated = onProfileCreated,
    )
}

@Composable
private fun CreateProfileScreen(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    fullNameCheckState: CreateProfileStates.FullNameCheckState,
    birthDateInMillis: Long,
    onBirthDateInMillisChange: (Long) -> Unit,
    birthDateCheckState: CreateProfileStates.BirthDateCheckState,
    citizenId: String,
    onCitizenIdChange: (String) -> Unit,
    citizenIdCheckState: CreateProfileStates.CitizenIdCheckState,
    address: String,
    onAddressChange: (String) -> Unit,
    isFormValid: Boolean,
    message: String,
    onCreateProfile: (onSuccess: () -> Unit, onError: () -> Unit) -> Unit,
    onProfileCreated: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    fun showSnackbar() {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isFormValid) {
                ExtendedFloatingActionButton(
                    onClick = {
                        onCreateProfile(
                            onProfileCreated
                        ) { showSnackbar() }
                    },
                ) {
                    Text("Next")
                }
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { onFullNameChange(it) },
                label = { Text("Full Name") },
                isError = !fullNameCheckState.isValid,
                supportingText = {
                    fullNameCheckState.errorMessage?.let { Text(it) }
                },
                modifier = Modifier.padding(bottom = 8.dp) // TODO: Space?
            )
            OutlinedTextField(
                value = birthDateInMillis.toDateString(),
                onValueChange = { onBirthDateInMillisChange(0L) }, // TODO: This add picker
                label = { Text("Birth Date") },
                placeholder = { Text(datePattern) },
                isError = !birthDateCheckState.isValid,
                supportingText = {
                    birthDateCheckState.errorMessage?.let {
                        Text(it)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = citizenId,
                onValueChange = { onCitizenIdChange(it) },
                label = { Text("Citizen ID") },
                isError = !citizenIdCheckState.isValid,
                supportingText = {
                    citizenIdCheckState.errorMessage?.let { Text(it) }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = { onAddressChange(it) },
                label = { Text("Address (Optional)") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CreateProfileScreenPreview() {
    CoinhubTheme {
        CreateProfileScreen(
            fullName = "KevinNitro",
            onFullNameChange = {},
            fullNameCheckState = CreateProfileStates.FullNameCheckState(
                isValid = false, errorMessage = "Bad"
            ),
            birthDateInMillis = 10000L,
            onBirthDateInMillisChange = {},
            birthDateCheckState = CreateProfileStates.BirthDateCheckState(
                isValid = false, errorMessage = "Cool"
            ),
            citizenId = "123",
            onCitizenIdChange = {},
            citizenIdCheckState = CreateProfileStates.CitizenIdCheckState(
                isValid = false, errorMessage = "Less"
            ),
            address = "",
            onAddressChange = {},
            isFormValid = true,
            message = "Wow",
            onCreateProfile = { _, _ -> },
            onProfileCreated = {},
        )
    }
}