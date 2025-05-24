package com.coinhub.android.presentation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.screens.auth.components.AuthHeader
import com.coinhub.android.presentation.screens.auth.components.CredentialInput
import com.coinhub.android.presentation.screens.auth.components.OAuth
import com.coinhub.android.presentation.screens.auth.components.OrDivider
import com.coinhub.android.presentation.screens.auth.components.SignInOrUpPrompt
import com.coinhub.android.presentation.viewmodels.AuthViewModel
import com.coinhub.android.ui.theme.CoinhubTheme

// TODO: The params name, check them
@Composable
fun AuthScreen(
    onSignIn: () -> Unit = {},
    onSignUp: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val isSignUp by viewModel.isSignUp.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AuthHeader(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        CredentialInput(
            modifier = Modifier.fillMaxWidth(), isSignUp = isSignUp, viewModel = viewModel
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isSignUp) {
                    // TODO: @NTGNguyen - Handle sign up
                    onSignUp()
                } else {
                    // TODO: @NTGNguyen - Handle sign in
                    onSignIn()
                }
            },
        ) { }
        Spacer(modifier = Modifier.height(16.dp))
        SignInOrUpPrompt(
            modifier = Modifier.fillMaxWidth(), isSignUp = isSignUp, onToggle = {
                viewModel.setSignUp(it)
            })
        Spacer(modifier = Modifier.height(24.dp))
        OrDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        // TODO: @NTGNguyen - Passing things?
        OAuth()
    }
}


@Preview
@Composable
fun SignInScreenPreview() {
    CoinhubTheme {
        AuthScreen()
    }
}
