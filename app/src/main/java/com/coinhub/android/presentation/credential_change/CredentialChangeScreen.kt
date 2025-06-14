package com.coinhub.android.presentation.credential_change

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.presentation.common.components.PasswordInputBox
import com.coinhub.android.presentation.credential_change.components.TopUpTopBar
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun CredentialChangeScreen(
    onBack: () -> Unit,
    viewModel: CredentialChangeViewModel = hiltViewModel(),
) {
    val currentPassword = viewModel.currentPassword.collectAsStateWithLifecycle().value
    val newPassword = viewModel.newPassword.collectAsStateWithLifecycle().value
    val confirmPassword = viewModel.confirmPassword.collectAsStateWithLifecycle().value
    val showPasswords = viewModel.showPasswords.collectAsStateWithLifecycle().value
    val currentPasswordState = viewModel.currentPasswordState.collectAsStateWithLifecycle().value
    val newPasswordState = viewModel.newPasswordState.collectAsStateWithLifecycle().value
    val confirmPasswordState = viewModel.confirmPasswordState.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message.contains("success", ignoreCase = true)) {
                onBack()
            }
        }
    }

    CredentialChangeScreen(
        currentPassword = currentPassword,
        onCurrentPasswordChange = viewModel::updateCurrentPassword,
        currentPasswordState = currentPasswordState,
        newPassword = newPassword,
        onNewPasswordChange = viewModel::updateNewPassword,
        newPasswordState = newPasswordState,
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        confirmPasswordState = confirmPasswordState,
        showPasswords = showPasswords,
        onToggleShowPasswords = viewModel::toggleShowPasswords,
        isFormValid = isFormValid,
        onChangeCredential = viewModel::changeCredential,
        onBack = onBack
    )
}

@Composable
private fun CredentialChangeScreen(
    currentPassword: String,
    onCurrentPasswordChange: (String) -> Unit,
    currentPasswordState: CredentialChangeStates.CurrentPasswordState,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    newPasswordState: CredentialChangeStates.NewPasswordState,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordState: CredentialChangeStates.ConfirmPasswordState,
    showPasswords: Boolean,
    onToggleShowPasswords: () -> Unit,
    isFormValid: Boolean,
    onChangeCredential: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopUpTopBar(onBack = onBack)
        },
        floatingActionButton = {
            if (isFormValid) {
                FloatingActionButton(
                    onClick = onChangeCredential,
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Change Password"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Change your account password by entering your current password and a new password.",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            PasswordInputBox(
                modifier = Modifier.fillMaxWidth(),
                password = currentPassword,
                onPasswordChange = onCurrentPasswordChange,
                isValid = currentPasswordState.isValid,
                errorMessage = currentPasswordState.errorMessage,
                label = "Current Password",
                showPassword = showPasswords,
                setShowPassword = { onToggleShowPasswords() },
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(4.dp))

            PasswordInputBox(
                modifier = Modifier.fillMaxWidth(),
                password = newPassword,
                onPasswordChange = onNewPasswordChange,
                isValid = newPasswordState.isValid,
                errorMessage = newPasswordState.errorMessage,
                label = "New Password",
                showPassword = showPasswords,
                setShowPassword = { onToggleShowPasswords() },
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(4.dp))

            PasswordInputBox(
                modifier = Modifier.fillMaxWidth(),
                password = confirmPassword,
                onPasswordChange = onConfirmPasswordChange,
                isValid = confirmPasswordState.isValid,
                errorMessage = confirmPasswordState.errorMessage,
                label = "Confirm New Password",
                showPassword = showPasswords,
                setShowPassword = { onToggleShowPasswords() },
                imeAction = ImeAction.Done
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CoinhubTheme {
        Surface {
            CredentialChangeScreen(
                currentPassword = "",
                onCurrentPasswordChange = {},
                currentPasswordState = CredentialChangeStates.CurrentPasswordState(),
                newPassword = "",
                onNewPasswordChange = {},
                newPasswordState = CredentialChangeStates.NewPasswordState(),
                confirmPassword = "",
                onConfirmPasswordChange = {},
                confirmPasswordState = CredentialChangeStates.ConfirmPasswordState(),
                showPasswords = false,
                onToggleShowPasswords = {},
                isFormValid = false,
                onChangeCredential = {},
                onBack = {}
            )
        }
    }
}
