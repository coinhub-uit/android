package com.coinhub.android.presentation.auth.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
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

    var showPassword by remember { mutableStateOf(false) }

    fun setShowPassword(show: Boolean) {
        showPassword = show
    }

    Column(modifier = modifier) {
        EmailInputBox(
            email = email,
            onEmailChange = { onEmailChange(it) },
            isValid = emailCheckState.isValid,
            errorMessage = emailCheckState.errorMessage,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        )

        // Password
        PasswordInputBox(
            password = password,
            onPasswordChange = { onPasswordChange(it) },
            isValid = passwordCheckState.isValid,
            errorMessage = passwordCheckState.errorMessage,
            showPassword = showPassword,
            setShowPassword = ::setShowPassword,
            contentType = ContentType.NewPassword,
            imeAction = if (isSignUp) ImeAction.Next else ImeAction.Done,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        )

        AnimatedVisibility(
            visible = isSignUp,
        ) {
            PasswordInputBox(
                password = confirmPassword,
                onPasswordChange = { onConfirmPasswordChange(it) },
                isValid = confirmPasswordCheckState.isValid,
                errorMessage = confirmPasswordCheckState.errorMessage,
                showPassword = showPassword,
                setShowPassword = ::setShowPassword,
                imeAction = ImeAction.Done,
                contentType = ContentType.NewPassword,
                label = "Confirm Password",
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            )
        }
    }
}
