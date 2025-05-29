package com.coinhub.android.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
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
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val checkUserSignedInUseCase: CheckUserSignedInUseCase,
) : ViewModel() {
    var message = "" // WARN: This is for snackbar to popup, I don't know which name is better for this
        private set

    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> = _isUserSignedIn.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val emailCheckState = email.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validateEmailUseCase(it)
        AuthStates.EmailCheckState(
            isValid = result is ValidateEmailUseCase.Result.Success,
            errorMessage = if (result is ValidateEmailUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.EmailCheckState())

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val passwordCheckState = password.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validatePasswordUseCase(it)
        AuthStates.PasswordCheckState(
            isValid = result is ValidatePasswordUseCase.Result.Success,
            errorMessage = if (result is ValidatePasswordUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.PasswordCheckState())

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val confirmPasswordCheckState = confirmPassword.drop(1).debounce(DEBOUNCE_TYPING).map {
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
        emailCheckState.isValid && passwordCheckState.isValid && (!isSignup or confirmPasswordCheckState.isValid)
    }.drop(1).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

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

    fun checkUserSignedIn() {
        viewModelScope.launch {
            when (val result = checkUserSignedInUseCase()) {
                is CheckUserSignedInUseCase.Result.Error -> {
                    message = result.message
                    _isUserSignedIn.value = false
                }

                is CheckUserSignedInUseCase.Result.Success -> {
                    _isUserSignedIn.value = result.isSignedIn
                }
            }
        }
    }

    fun signInWithCredential(
        onSuccess: () -> Unit, onError: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signInWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignInWithCredentialUseCase.Result.Error -> {
                    message = result.message
                    onError()
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
                }

                is SignUpWithCredentialUseCase.Result.Success -> {
                    onSuccess()
                }
            }
        }
    }

    // FIXME: WTF args?
    fun onSignInWithGoogle(
        signInResult: NativeSignInResult,
        onSignedIn: () -> Unit,
        onSignedUp: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signInWithGoogleUseCase(signInResult)) {
                is SignInWithGoogleUseCase.Result.Error -> {
                    message = result.message
                }

                is SignInWithGoogleUseCase.Result.Success -> {
                    Log.d("hehehehehhe", "onSignInWithGoogle: aklhdwsjxwkh")
                    if (result.googleNavigateResult.isUserRegisterProfile) {
                        onSignedIn()
                    } else {
                        onSignedUp()
                    }
                }
            }
        }
    }
}
