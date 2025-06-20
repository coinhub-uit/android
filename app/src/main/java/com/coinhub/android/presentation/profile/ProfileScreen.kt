package com.coinhub.android.presentation.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SensorOccupied
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.presentation.common.components.DatePickerModal
import com.coinhub.android.presentation.menu.components.MenuAvatarPicker
import com.coinhub.android.presentation.profile.components.ProfileTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.datePattern
import com.coinhub.android.utils.toDateString

// NOTE: This screen is bruh with is "edit" or not. It's suck
@Composable
fun ProfileScreen(
    onBack: (() -> Unit)? = null,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val avatarUri = viewModel.avatarUri.collectAsStateWithLifecycle().value
    val fullName = viewModel.fullName.collectAsStateWithLifecycle().value
    val fullNameCheckState = viewModel.fullNameCheckState.collectAsStateWithLifecycle().value
    val birthDateInMillis = viewModel.birthDateInMillis.collectAsStateWithLifecycle().value
    val birthDateCheckState = viewModel.birthDateCheckState.collectAsStateWithLifecycle().value
    val citizenId = viewModel.citizenId.collectAsStateWithLifecycle().value
    val citizenIdCheckState = viewModel.citizenIdCheckState.collectAsStateWithLifecycle().value
    val address = viewModel.address.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (onBack != null) {
            viewModel.loadProfile() // It means user is editing his profile
        }
    }

    ProfileScreen(
        onBack = onBack,
        avatarUri = avatarUri,
        onAvatarUriChange = viewModel::onAvatarUriChange,
        fullName = fullName,
        onFullNameChange = viewModel::onFullNameChange,
        fullNameCheckState = fullNameCheckState,
        birthDateInMillis = birthDateInMillis,
        onBirthDateInMillisChange = viewModel::onBirthDateInMillisChange,
        birthDateCheckState = birthDateCheckState,
        citizenId = citizenId,
        onCitizenIdChange = viewModel::onCitizenIdChange,
        citizenIdCheckState = citizenIdCheckState,
        address = address,
        onAddressChange = viewModel::onAddressChange,
        isFormValid = isFormValid,
        onCreateProfile = viewModel::onCreateProfile,
        onUpdateProfile = if (onBack != null) {
            { viewModel.onUpdateProfile(onSuccess = onBack) }
        } else null,
    )
}

@Composable
private fun ProfileScreen(
    onBack: (() -> Unit)?,
    avatarUri: Uri?,
    onAvatarUriChange: (Uri) -> Unit,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    fullNameCheckState: ProfileStates.FullNameCheckState,
    birthDateInMillis: Long,
    onBirthDateInMillisChange: (Long) -> Unit,
    birthDateCheckState: ProfileStates.BirthDateCheckState,
    citizenId: String,
    onCitizenIdChange: (String) -> Unit,
    citizenIdCheckState: ProfileStates.CitizenIdCheckState,
    address: String,
    onAddressChange: (String) -> Unit,
    isFormValid: Boolean,
    onCreateProfile: () -> Unit,
    onUpdateProfile: (() -> Unit)?,
) {
    val isEdit = remember(onBack, onUpdateProfile) { onBack != null && onUpdateProfile != null }
    var isDatePickerShowed by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ProfileTopBar(isEdit = isEdit, onBack = onBack) },
        floatingActionButton = {
            if (isFormValid) {
                ExtendedFloatingActionButton(
                    onClick = if (isEdit) onUpdateProfile!! else onCreateProfile,
                ) {
                    Text(if (isEdit) "Save" else "Next")
                }
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuAvatarPicker(avatarUri = avatarUri, onAvatarUriChange = onAvatarUriChange)
            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                label = { Text("Full Name") },
                singleLine = true,
                isError = !fullNameCheckState.isValid,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                supportingText = {
                    fullNameCheckState.errorMessage?.let { Text(it) }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = "Full Name"
                    )
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .semantics {
                        contentType = ContentType.PersonFullName
                    })
            OutlinedTextField(
                value = birthDateInMillis.toDateString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Birth Date") },
                placeholder = { Text(datePattern) },
                isError = !birthDateCheckState.isValid,
                supportingText = {
                    birthDateCheckState.errorMessage?.let {
                        Text(it)
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { isDatePickerShowed = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange, contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = citizenId,
                onValueChange = onCitizenIdChange,
                label = { Text("Citizen ID") },
                isError = !citizenIdCheckState.isValid,
                supportingText = {
                    citizenIdCheckState.errorMessage?.let { Text(it) }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.SensorOccupied, contentDescription = "Citizen ID"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = onAddressChange,
                label = { Text("Address (Optional)") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home, contentDescription = "Address"
                    )
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .semantics {
                        contentType = ContentType.AddressStreet
                    },
            )
        }
    }

    if (isDatePickerShowed) {
        DatePickerModal(
            onDateSelected = onBirthDateInMillisChange,
            onDismiss = { isDatePickerShowed = false },
            selectedDate = birthDateInMillis
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun CreateProfileScreenPreview() {
    CoinhubTheme {
        ProfileScreen(
            onBack = {},
            fullName = "KevinNitro",
            onFullNameChange = {},
            fullNameCheckState = ProfileStates.FullNameCheckState(
                isValid = false, errorMessage = "Bad"
            ),
            birthDateInMillis = 10000L,
            onBirthDateInMillisChange = {},
            birthDateCheckState = ProfileStates.BirthDateCheckState(
                isValid = false, errorMessage = "Cool"
            ),
            citizenId = "123",
            onCitizenIdChange = {},
            citizenIdCheckState = ProfileStates.CitizenIdCheckState(
                isValid = false, errorMessage = "Less"
            ),
            address = "",
            onAddressChange = {},
            isFormValid = true,
            onCreateProfile = {},
            onUpdateProfile = null,
            avatarUri = "https://placehold.co/150".toUri(),
            onAvatarUriChange = {},
        )
    }
}