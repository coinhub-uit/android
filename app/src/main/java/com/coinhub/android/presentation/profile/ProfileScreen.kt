package com.coinhub.android.presentation.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
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
import com.coinhub.android.presentation.navigation.app.components.AvatarPicker
import com.coinhub.android.presentation.profile.components.ProfileTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.datePattern
import com.coinhub.android.utils.toDateString
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    isEdit: Boolean,
    onSuccess: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val avatarUri = viewModel.avatarUri.collectAsStateWithLifecycle().value
    val onAvatarUriChange = viewModel::onAvatarUriChange
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

    ProfileScreen(
        isEdit = isEdit,
        avatarUri = avatarUri,
        onAvatarUriChange = onAvatarUriChange,
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
        onProfileCreated = onSuccess,
    )
}

@Composable
private fun ProfileScreen(
    isEdit: Boolean,
    avatarUri: Uri,
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
    message: String,
    onCreateProfile: (onSuccess: () -> Unit, onError: () -> Unit) -> Unit,
    onProfileCreated: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isDatePickerShowed by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    fun showSnackbar() {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ProfileTopBar(isEdit = isEdit) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (isFormValid) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(16.dp),
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
            AvatarPicker(avatarUri = avatarUri, onAvatarUriChange = onAvatarUriChange)
            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                label = { Text("Full Name") },
                singleLine = true,
                isError = !fullNameCheckState.isValid,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
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
                    })

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
            isEdit = true,
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
            message = "Wow",
            onCreateProfile = { _, _ -> },
            onProfileCreated = {},
            avatarUri = "https://placehold.co/150".toUri(),
            onAvatarUriChange = {}
        )
    }
}