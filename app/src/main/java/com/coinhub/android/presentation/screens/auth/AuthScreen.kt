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
import com.coinhub.android.presentation.screens.auth.components.CredentialInput
import com.coinhub.android.presentation.screens.auth.components.OAuth
import com.coinhub.android.presentation.screens.auth.components.OrDivider
import com.coinhub.android.presentation.screens.auth.components.SignInOrUpPrompt
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
        CredentialInput(modifier = Modifier.fillMaxWidth())
        SignInOrUpButton(modifier = Modifier.fillMaxWidth(), onSignIn = onSignIn, onSignUp = onSignUp)
        Spacer(modifier = Modifier.height(16.dp))
        SignInOrUpPrompt(modifier = Modifier.fillMaxWidth())
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
