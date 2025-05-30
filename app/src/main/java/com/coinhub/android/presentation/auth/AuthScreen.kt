package com.coinhub.android.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInState
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun AuthScreen(
    onSignedIn: () -> Unit,
    onSignedUp: () -> Unit,
    supabaseClient: SupabaseClient,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val isSignUp = viewModel.isSignUp.collectAsStateWithLifecycle().value
    val setIsSignUp = viewModel::setIsSignUp
    val email = viewModel.email.collectAsStateWithLifecycle().value
    val onEmailChange = viewModel::onEmailChange
    val emailCheckState = viewModel.emailCheckState.collectAsStateWithLifecycle().value
    val password = viewModel.password.collectAsStateWithLifecycle().value
    val onPasswordChange = viewModel::onPasswordChange
    val passwordCheckState = viewModel.passwordCheckState.collectAsStateWithLifecycle().value
    val confirmPassword = viewModel.confirmPassword.collectAsStateWithLifecycle().value
    val onConfirmPasswordChange = viewModel::onConfirmPasswordChange
    val confirmPasswordCheckState = viewModel.confirmPasswordCheckState.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    val snackbarMessage = viewModel.snackbarMessage.collectAsStateWithLifecycle().value
    val clearSnackBarMessage = viewModel::clearSnackbarMessage
    val signUpWithCredential = viewModel::signUpWithCredential
    val signInWithCredential = viewModel::signInWithCredential
    val onSignInWithGoogle = viewModel::onSignInWithGoogle

    val googleSignInState = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { signInResult ->
            onSignInWithGoogle(
                signInResult,
                onSignedIn,
                onSignedUp
            )
        }
    )

    AuthScreen(
        isSignUp = isSignUp,
        setIsSignUp = setIsSignUp,
        email = email,
        onEmailChange = onEmailChange,
        emailCheckState = emailCheckState,
        password = password,
        onPasswordChange = onPasswordChange,
        passwordCheckState = passwordCheckState,
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = onConfirmPasswordChange,
        confirmPasswordCheckState = confirmPasswordCheckState,
        isFormValid = isFormValid,
        signUpWithCredential = signUpWithCredential,
        signInWithCredential = signInWithCredential,
        onSignedIn = onSignedIn,
        onSignedUp = onSignedUp,
        snackbarMessage = snackbarMessage,
        clearSnackBarMessage = clearSnackBarMessage,
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
    signUpWithCredential: (onSuccess: () -> Unit) -> Unit,
    signInWithCredential: (onSuccess: () -> Unit) -> Unit,
    onSignedIn: () -> Unit,
    onSignedUp: () -> Unit,
    snackbarMessage: String?,
    clearSnackBarMessage: () -> Unit,
    googleSignInState: NativeSignInState?,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (snackbarMessage != null) {
        LaunchedEffect(snackbarMessage) {
            snackbarHostState.showSnackbar(
                snackbarMessage,
                duration = SnackbarDuration.Short
            )
            clearSnackBarMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AuthHeader(
                    modifier = Modifier
                        .width(128.dp)
                        .padding(bottom = 64.dp)
                )
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
                AuthSignInOrUpButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    isSignUp = isSignUp,
                    onSignUp = {
                        signUpWithCredential(onSignedUp)
                    }, onSignIn = {
                        signInWithCredential(onSignedIn)
                    }, isFormValid = isFormValid
                )
                AuthSignInOrUpPrompt(
                    modifier = Modifier.fillMaxWidth(),
                    isSignUp = isSignUp,
                    setIsSignUp = setIsSignUp,
                )
                AuthOrDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
                // TODO: @NTGNguyen - Passing things?
                AuthOAuth(
                    onClick = {
                        googleSignInState?.startFlow()
                    },
                    modifier = Modifier.width(
                        80.dp
                    ),
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
            onSignedIn = {},
            onSignedUp = {},
            signInWithCredential = { },
            signUpWithCredential = { },
            googleSignInState = null,
            snackbarMessage = null,
            clearSnackBarMessage = {}
        )
    }
}
