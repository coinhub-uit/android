package com.coinhub.android.presentation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.screens.auth.components.AuthHeader
import com.coinhub.android.presentation.screens.auth.components.AuthCredentialInput
import com.coinhub.android.presentation.screens.auth.components.AuthOAuth
import com.coinhub.android.presentation.screens.auth.components.AuthOrDivider
import com.coinhub.android.presentation.screens.auth.components.AuthSignInOrUpButton
import com.coinhub.android.presentation.screens.auth.components.AuthSignInOrUpPrompt
import com.coinhub.android.ui.theme.CoinhubTheme

// TODO: The params name, check them. Or... let hilt do the job?
@Composable
fun AuthScreen(
    onSignIn: () -> Unit = {},
    onSignUp: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AuthHeader(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        AuthCredentialInput(modifier = Modifier.fillMaxWidth())
        AuthSignInOrUpButton(modifier = Modifier.fillMaxWidth(), onSignIn = onSignIn, onSignUp = onSignUp)
        Spacer(modifier = Modifier.height(16.dp))
        AuthSignInOrUpPrompt(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        AuthOrDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        // TODO: @NTGNguyen - Passing things?
        AuthOAuth()
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    CoinhubTheme {
        AuthScreen()
    }
}
