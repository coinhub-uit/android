package com.coinhub.android.presentation.screens.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.viewmodels.AuthViewModel

@Composable
fun SignInOrUpButton(
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    viewModel : AuthViewModel = hiltViewModel(),
) {
    val isSignUp by viewModel.isSignUp.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()

    Button(
        modifier = modifier,
        enabled = isFormValid,
        onClick = {
            if (isSignUp) {
                // TODO: @NTGNguyen - Handle sign up
                onSignUp()
            } else {
                // TODO: @NTGNguyen - Handle sign in
                onSignIn()
            }
        },
    ) {
        Text(if (isSignUp) "Sign Up" else "Sign In")
    }
}