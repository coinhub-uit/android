package com.coinhub.android.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AuthSignInOrUpPrompt(
    modifier: Modifier = Modifier,
    isSignUp : Boolean,
    setIsSignUp: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isSignUp) "Don't have an account?" else "Already have an account?",
            style = MaterialTheme.typography.bodyMedium
        )
        TextButton(
            onClick = { setIsSignUp(!isSignUp) },
        ) {
            Text(
                text = if (isSignUp) "Sign In" else "Create one",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
