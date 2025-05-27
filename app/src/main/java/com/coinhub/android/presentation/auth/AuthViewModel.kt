package com.coinhub.android.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.HandleResultOnSignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val signUpWithCredentialUseCase: SignUpWithCredentialUseCase,
    private val handleResultOnSignInWithGoogleUseCase: HandleResultOnSignInWithGoogleUseCase,
) : ViewModel() {
    var message = "" // WARN: This is for snackbar to popup, I don't know which name is better for this
        private set

    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val emailCheckState = email.debounce(DEBOUNCE_TYPING).mapLatest {
        val result = validateEmailUseCase(it)
        AuthStates.EmailCheckState(
            isValid = result is ValidateEmailUseCase.Result.Success,
            errorMessage = if (result is ValidateEmailUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.EmailCheckState())

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val passwordCheckState = password.debounce(DEBOUNCE_TYPING).mapLatest {
        val result = validatePasswordUseCase(it)
        AuthStates.PasswordCheckState(
            isValid = result is ValidatePasswordUseCase.Result.Success,
            errorMessage = if (result is ValidatePasswordUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.PasswordCheckState())

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val confirmPasswordCheckState = confirmPassword.debounce(DEBOUNCE_TYPING).mapLatest {
        val result = validateConfirmPasswordUseCase(
            _password.value, it
        )
        AuthStates.ConfirmPasswordCheckState(
            isValid = result is ValidateConfirmPasswordUseCase.Result.Success,
            errorMessage = if (result is ValidateConfirmPasswordUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.ConfirmPasswordCheckState())

    val isFormValid: StateFlow<Boolean> = combine(
        emailCheckState, passwordCheckState, confirmPasswordCheckState, isSignUp
    ) { emailCheckState, passwordCheckState, confirmPasswordCheckState, isSignup ->
        emailCheckState.isValid && passwordCheckState.isValid && (isSignup && confirmPasswordCheckState.isValid)
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setIsSignUp(isSignUp: Boolean) {
        _isSignUp.value = isSignUp
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun signInWithCredential(
        onSuccess: () -> Unit, onError: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signInWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignInWithCredentialUseCase.Result.Error -> {
                    message = result.message
                    onError()
                    TODO()
                }

                is SignInWithCredentialUseCase.Result.Success -> {
                    onSuccess()
                }
            }
        }
    }

    fun signUpWithCredential(
        onSuccess: () -> Unit, onError: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signUpWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignUpWithCredentialUseCase.Result.Error -> {
                    message = result.message
                    onError()
                    TODO()
                }

                is SignUpWithCredentialUseCase.Result.Success -> {
                    onSuccess()
                }
            }
        }
    }

    // FIXME: WTF args?
    fun handleResultOnSignInWithGoogle(
        signInResult: NativeSignInResult,
        onNavigateToHomeScreen: (String) -> Unit,
        onNavigateToRegisterProfile: () -> Unit,
        onError: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = handleResultOnSignInWithGoogleUseCase(signInResult)) {
                is HandleResultOnSignInWithGoogleUseCase.Result.Error -> {
                    message = result.message
                    onError()
                    TODO()
                }

                is HandleResultOnSignInWithGoogleUseCase.Result.Success -> {
                    if (result.googleNavigateResult.isUserRegisterProfile) {
                        onNavigateToHomeScreen(result.googleNavigateResult.userId)
                    } else {
                        onNavigateToRegisterProfile()
                    }
                }
            }
        }
    }
}
