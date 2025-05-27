package com.coinhub.android.presentation.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.auth.AuthStates
import com.coinhub.android.presentation.common.components.EmailInputBox
import com.coinhub.android.presentation.common.components.PasswordInputBox

@Composable
fun AuthCredentialInput(
    modifier: Modifier = Modifier,
    isSignUp: Boolean,
    email: String,
    onEmailChange: (String) -> Unit,
    emailCheckState: AuthStates.EmailCheckState,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordCheckState: AuthStates.PasswordCheckState,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordCheckState: AuthStates.ConfirmPasswordCheckState,
) {

    Column(modifier = modifier) {

        EmailInputBox(
            email = email,
            onEmailChange = { onEmailChange(it) },
            isValid = emailCheckState.isValid,
            errorMessage = emailCheckState.errorMessage,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Password
        PasswordInputBox(
            password = password,
            onPasswordChange = { onPasswordChange(it) },
            isValid = passwordCheckState.isValid,
            errorMessage = passwordCheckState.errorMessage,
            imeAction = if (isSignUp) ImeAction.Next else ImeAction.Done,
            modifier = Modifier.fillMaxWidth(),
        )

        // Confirm Password
        if (isSignUp) {
            Spacer(modifier = Modifier.height(10.dp))

            PasswordInputBox(
                password = confirmPassword,
                onPasswordChange = { onConfirmPasswordChange(it) },
                isValid = confirmPasswordCheckState.isValid,
                errorMessage = confirmPasswordCheckState.errorMessage,
                imeAction = ImeAction.Done,
                label = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
