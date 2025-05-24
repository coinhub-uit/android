package com.coinhub.android.presentation.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.components.EmailInputBox
import com.coinhub.android.presentation.components.PasswordInputBox
import com.coinhub.android.presentation.viewmodels.AuthViewModel

@Composable
fun CredentialInput(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val isSignUp by viewModel.isSignUp.collectAsState()

    Column(modifier = modifier) {
        // Email
        val email by viewModel.email.collectAsState()
        val isEmailError by viewModel.isEmailError.collectAsState()

        EmailInputBox(
            email = email,
            onEmailChange = { viewModel.setEmail(it) },
            validateEmail = { viewModel.validateEmail(email) },
            isError = isEmailError,
            setIsError = { viewModel.setIsEmailError(it) },
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(10.dp))

        val password by viewModel.password.collectAsState()
        val isPasswordError by viewModel.isPasswordError.collectAsState()
        val supportingPasswordText by viewModel.supportingPasswordText.collectAsState()

        // Password
        PasswordInputBox(
            password = password,
            onPasswordChange = { viewModel.setPassword(it) },
            validatePassword = { viewModel.validatePassword() },
            isError = isPasswordError,
            setIsError = { viewModel.setIsPasswordError(it) },
            supportingText = supportingPasswordText,
            imeAction = if (isSignUp) ImeAction.Next else ImeAction.Done,
            modifier = Modifier.fillMaxWidth(),
        )

        // Confirm Password
        if (isSignUp) {
            val confirmPassword by viewModel.confirmPassword.collectAsState()
            val isConfirmPasswordError by viewModel.isConfirmPasswordError.collectAsState()
            val supportingConfirmPasswordText by viewModel.supportingConfirmPasswordText.collectAsState()

            Spacer(modifier = Modifier.height(10.dp))

            PasswordInputBox(
                password = confirmPassword,
                onPasswordChange = { viewModel.setConfirmPassword(it) },
                validatePassword = { viewModel.validateConfirmPassword() },
                isError = isConfirmPasswordError,
                setIsError = { viewModel.setIsConfirmPasswordError(it) },
                supportingText = supportingConfirmPasswordText,
                label = "Confirm Password",
                imeAction = ImeAction.Done,
            )
        }
    }
}
