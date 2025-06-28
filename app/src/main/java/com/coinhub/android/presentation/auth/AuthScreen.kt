package com.coinhub.android.presentation.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.presentation.auth.components.AuthCredentialInput
import com.coinhub.android.presentation.auth.components.AuthHeader
import com.coinhub.android.presentation.auth.components.AuthOAuth
import com.coinhub.android.presentation.auth.components.AuthOrDivider
import com.coinhub.android.presentation.auth.components.AuthSignInOrUpButton
import com.coinhub.android.presentation.auth.components.AuthSignInOrUpPrompt
import com.coinhub.android.presentation.auth.managers.AccountManager
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import io.github.jan.supabase.compose.auth.composable.NativeSignInState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onSignedUpWithCredential: () -> Unit,
    onSignedUpWithOAuth: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val isSignUp = viewModel.isSignUp.collectAsStateWithLifecycle().value
    val email = viewModel.email.collectAsStateWithLifecycle().value
    val emailCheckState = viewModel.emailCheckState.collectAsStateWithLifecycle().value
    val password = viewModel.password.collectAsStateWithLifecycle().value
    val passwordCheckState = viewModel.passwordCheckState.collectAsStateWithLifecycle().value
    val confirmPassword = viewModel.confirmPassword.collectAsStateWithLifecycle().value
    val confirmPasswordCheckState = viewModel.confirmPasswordCheckState.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    val googleSignInState = viewModel.supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { signInResult ->
            viewModel.onSignInWithGoogle(
                signInResult, onSignedUpWithOAuth
            )
        })
    val isProcessing = viewModel.isProcessing.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    LaunchedEffect(Unit) {
        val result = accountManager.signIn()
        if (result is AccountManager.SignInResult.Success) {
            viewModel.onEmailChange(result.email)
            viewModel.onPasswordChange(result.password)
            viewModel.signInWithCredential(
                onProfileNotAvailable = onSignedUpWithOAuth
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    AuthScreen(
        isSignUp = isSignUp,
        setIsSignUp = viewModel::setIsSignUp,
        email = email,
        onEmailChange = viewModel::onEmailChange,
        emailCheckState = emailCheckState,
        password = password,
        onPasswordChange = viewModel::onPasswordChange,
        passwordCheckState = passwordCheckState,
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        confirmPasswordCheckState = confirmPasswordCheckState,
        isFormValid = isFormValid,
        isProcessing = isProcessing,
        signUpWithCredential = {
            viewModel.signUpWithCredential {
                scope.launch {
                    accountManager.signUp(email, password)
                    onSignedUpWithCredential()
                }
            }
        },
        signInWithCredential = {
            viewModel.signInWithCredential(
                onProfileNotAvailable = onSignedUpWithOAuth
            )
        },
        googleSignInState = googleSignInState
    )
}

@Composable
private fun AuthScreen(
    isSignUp: Boolean,
    setIsSignUp: (Boolean) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    emailCheckState: AuthStates.EmailCheckState,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordCheckState: AuthStates.PasswordCheckState,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordCheckState: AuthStates.ConfirmPasswordCheckState,
    isFormValid: Boolean,
    isProcessing: Boolean,
    signUpWithCredential: () -> Unit,
    signInWithCredential: () -> Unit,
    googleSignInState: NativeSignInState?,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                AuthHeader(
                    modifier = Modifier
                        .width(128.dp)
                        .padding(bottom = 64.dp)
                )
            }

            item {
                AuthCredentialInput(
                    modifier = Modifier.fillMaxWidth(),
                    isSignUp = isSignUp,
                    email = email,
                    onEmailChange = onEmailChange,
                    emailCheckState = emailCheckState,
                    password = password,
                    onPasswordChange = onPasswordChange,
                    passwordCheckState = passwordCheckState,
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    confirmPasswordCheckState = confirmPasswordCheckState
                )
            }

            item {
                AuthSignInOrUpButton(
                    modifier = Modifier.fillMaxWidth(),
                    isSignUp = isSignUp,
                    onSignUp = signUpWithCredential,
                    onSignIn = signInWithCredential,
                    isFormValid = isFormValid,
                    isProcessing = isProcessing,
                )
            }

            item {
                AuthSignInOrUpPrompt(
                    modifier = Modifier.fillMaxWidth(),
                    isSignUp = isSignUp,
                    setIsSignUp = setIsSignUp,
                )
            }

            item {
                AuthOrDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
            }

            item {
                AuthOAuth(
                    onClick = {
                        googleSignInState?.startFlow()
                    },
                    isProcessing = isProcessing,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(80.dp),
                )
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SignInScreenPreview() {
    CoinhubTheme {
        AuthScreen(
            isSignUp = true,
            setIsSignUp = {},
            email = "kevinnitro@duck.com",
            onEmailChange = {},
            emailCheckState = AuthStates.EmailCheckState(isValid = false, errorMessage = "Bad"),
            password = "LookMe!",
            onPasswordChange = {},
            passwordCheckState = AuthStates.PasswordCheckState(isValid = false, errorMessage = "Bad"),
            confirmPassword = "GlanceMe?",
            onConfirmPasswordChange = {},
            confirmPasswordCheckState = AuthStates.ConfirmPasswordCheckState(
                isValid = false, errorMessage = "bad"
            ),
            isFormValid = true,
            isProcessing = false,
            signInWithCredential = { },
            signUpWithCredential = { },
            googleSignInState = null,
        )
    }
}
