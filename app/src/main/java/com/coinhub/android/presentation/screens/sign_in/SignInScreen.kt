package com.coinhub.android.presentation.screens.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.coinhub.android.authentication.presentation.components.AccountComponent
import com.coinhub.android.authentication.presentation.components.MyTextFieldComponent
import com.coinhub.android.authentication.presentation.components.PasswordTextFieldComponent
import com.coinhub.android.presentation.components.AuthHeader
import com.coinhub.android.presentation.viewmodels.SignInViewModel
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun SignInScreen(
    onRegisterClick: () -> Unit = {},
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AuthHeader(modifier = Modifier.fillMaxWidth())
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = "Email",
                    icon = Icons.Outlined.Email,
                    text = email,
                    onTextChange = { viewModel.setEmail(it) })
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Password",
                    icon = Icons.Outlined.Lock,
                    passwordText = password,
                    onPasswordChange = { viewModel.setPassword(it) })
            }
            AccountComponent(
                textQuery = "Don't have an account? ",
                textClickable = "Register",
                action = "Login",
                navController = navController,
                onButtonClick = {
                    supabaseViewModel.signIn(
                        context = context,
                        userEmail = emailText,
                        userPassword = passwordText,
                        navController = navController
                    )
                },
                onGoogleButtonClick = {
                    supabaseGoogleAction.startFlow()
                }
            )
        }
    }
}

@Preview()
@Composable
fun SignInScreenPreview() {
    CoinhubTheme {
        SignInScreen()
    }
}
