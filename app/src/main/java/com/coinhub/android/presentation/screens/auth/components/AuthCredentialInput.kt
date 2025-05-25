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
fun AuthCredentialInput(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val isSignUp by viewModel.isSignUp.collectAsState()

    Column(modifier = modifier) {
        // Email
        val emailState by viewModel.emailState.collectAsState()

        EmailInputBox(
            email = emailState.email,
            onEmailChange = { viewModel.onEmailChange(it) },
            isValid = emailState.isValid,
            errorMessage = emailState.errorMessage,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(10.dp))

        val passwordState by viewModel.passwordState.collectAsState()

        // Password
        PasswordInputBox(
            password = passwordState.password,
            onPasswordChange = { viewModel.onPasswordChange(it) },
            isValid = passwordState.isValid,
            errorMessage = passwordState.errorMessage,
            imeAction = if (isSignUp) ImeAction.Next else ImeAction.Done,
            modifier = Modifier.fillMaxWidth(),
        )

        // Confirm Password
        if (isSignUp) {
            val confirmPasswordState by viewModel.confirmPasswordState.collectAsState()

            Spacer(modifier = Modifier.height(10.dp))

            PasswordInputBox(
                password = confirmPasswordState.confirmPassword,
                onPasswordChange = { viewModel.onPasswordChange(it) },
                isValid = confirmPasswordState.isValid,
                errorMessage = confirmPasswordState.errorMessage,
                imeAction = ImeAction.Done,
                label = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
