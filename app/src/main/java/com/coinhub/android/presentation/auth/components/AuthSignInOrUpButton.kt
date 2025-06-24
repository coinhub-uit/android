package com.coinhub.android.presentation.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
    isProcessing: Boolean,
) {
    Button(
        modifier = modifier,
        enabled = isFormValid && !isProcessing,
        onClick = if (isSignUp) onSignUp else onSignIn,
    ) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = if (isSignUp) "Sign Up" else "Sign In"
        )
    }
}
