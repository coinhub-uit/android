package com.coinhub.android.authentication.presentation.login

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.coinhub.android.authentication.TestViewModel
import com.coinhub.android.authentication.presentation.components.AccountComponent
import com.coinhub.android.authentication.presentation.components.HeadingTextComponent
import com.coinhub.android.authentication.presentation.components.MyTextFieldComponent
import com.coinhub.android.authentication.presentation.components.NormalTextComponent
import com.coinhub.android.authentication.presentation.components.PasswordTextFieldComponent
import io.github.jan.supabase.compose.auth.composable.NativeSignInState

@Composable
fun LoginScreen(
    navController: NavHostController,
    supabaseViewModel: TestViewModel,
    context: Context,
    supabaseGoogleAction: NativeSignInState
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                NormalTextComponent(value = "Hey, there")
                HeadingTextComponent(value = "Welcome Back")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = "Email",
                    icon = Icons.Outlined.Email,
                    text = emailText,
                    onTextChange = { emailText = it })
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Password",
                    icon = Icons.Outlined.Lock,
                    passwordText = passwordText,
                    onPasswordChange = { passwordText = it })
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
                onGoogleButtionClick = {
                    supabaseGoogleAction.startFlow()
                }
            )
        }
    }
}


