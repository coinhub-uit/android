package com.coinhub.android.presentation.screens.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.viewmodels.AuthViewModel

@Composable
fun SignInOrUpPrompt(
    modifier: Modifier = Modifier,
    viewModel : AuthViewModel = hiltViewModel(),
) {
    val isSignUp by viewModel.isSignUp.collectAsState()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isSignUp) "Don't have an account? " else "Already have an account? ",
            style = MaterialTheme.typography.bodyMedium
        )
        TextButton(
            onClick = { viewModel.setIsSignUp(!isSignUp) },
        ) {
            Text(
                text = if (isSignUp) "Sign In" else "Create one",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
