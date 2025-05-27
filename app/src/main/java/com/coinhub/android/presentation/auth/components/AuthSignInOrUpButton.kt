package com.coinhub.android.presentation.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthSignInOrUpButton(
    modifier: Modifier = Modifier,
    isSignUp: Boolean,
    isFormValid: Boolean,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
) {
    Button(
        modifier = modifier, enabled = isFormValid, onClick = if (isSignUp) onSignUp else onSignIn
    ) {
        Text(if (isSignUp) "Sign Up" else "Sign In")
    }
}
