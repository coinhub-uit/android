package com.coinhub.android.presentation.screens.sign_up

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import com.coinhub.android.presentation.viewmodels.AuthViewModel
import com.coinhub.android.authentication.SupabaseViewModel
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.authentication.presentation.components.AccountComponent
import com.coinhub.android.authentication.presentation.components.CheckboxComponent
import com.coinhub.android.authentication.presentation.components.HeadingTextComponent
import com.coinhub.android.authentication.presentation.components.MyTextFieldComponent
import com.coinhub.android.authentication.presentation.components.NormalTextComponent
import com.coinhub.android.authentication.presentation.components.PasswordTextFieldComponent
import io.github.jan.supabase.compose.auth.composable.NativeSignInState
import java.util.Date

@Composable
fun SignupScreen(
    navController: NavHostController,
    supabaseViewModel: SupabaseViewModel,
    context: Context,
    supabaseGoogleAction: NativeSignInState,
    apiServerViewModel: AuthViewModel
) {
    var fullNameText by remember { mutableStateOf("") }
    var birthDateText by remember { mutableStateOf("") }
    var citizenId by remember { mutableStateOf("") }
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
            NormalTextComponent(value = "Hello there,")
            HeadingTextComponent(value = "Create an Account")
            Spacer(modifier = Modifier.height(25.dp))

            Column {
                MyTextFieldComponent(
                    labelValue = "Full Name",
                    icon = Icons.Outlined.Person,
                    text = fullNameText,
                    onTextChange = { fullNameText = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponent(
                    labelValue = "BirthDate",
                    icon = Icons.Outlined.Cake,
                    text = birthDateText,
                    onTextChange = { birthDateText = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponent(
                    labelValue = "Citizen ID",
                    icon = Icons.Outlined.CreditCard,
                    text = citizenId,
                    onTextChange = { citizenId = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldComponent(
                    labelValue = "Email",
                    icon = Icons.Outlined.Email,
                    text = emailText,
                    onTextChange = { emailText = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Password",
                    icon = Icons.Outlined.Lock,
                    passwordText = passwordText,
                    onPasswordChange = { passwordText = it }
                )
                CheckboxComponent()
                AccountComponent(
                    textQuery = "Already have an account? ",
                    textClickable = "Login",
                    action = "Register",
                    navController = navController,
                    onButtonClick = {
                        supabaseViewModel.signUp(
                            context = context,
                            userPassword = passwordText,
                            userEmail = emailText,
                            navController = navController
                        )
                        apiServerViewModel.registerProfile(
                            CreateUserDto(
                                birthDate = Date(birthDateText),
                                citizenId = citizenId,
                                id = "${supabaseViewModel.userId.value}",
                                fullName = fullNameText
                            )
                        )
                    },
                    onGoogleButtonClick = {
                        supabaseGoogleAction.startFlow()
                    }
                )
            }
        }
    }
}


