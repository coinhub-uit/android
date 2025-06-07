package com.coinhub.android.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.use_cases.CheckProfileAvailableUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
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
    private val supabaseService: SupabaseService,
    private val checkProfileAvailableUseCase: CheckProfileAvailableUseCase,
    val supabaseClient: SupabaseClient,
) : ViewModel() {
    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    @OptIn(FlowPreview::class)
    val emailCheckState = email.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validateEmailUseCase(it)
        AuthStates.EmailCheckState(
            isValid = result is ValidateEmailUseCase.Result.Success,
            errorMessage = if (result is ValidateEmailUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.EmailCheckState())

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    @OptIn(FlowPreview::class)
    val passwordCheckState = password.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validatePasswordUseCase(it)
        AuthStates.PasswordCheckState(
            isValid = result is ValidatePasswordUseCase.Result.Success,
            errorMessage = if (result is ValidatePasswordUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthStates.PasswordCheckState())

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    @OptIn(FlowPreview::class)
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

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    var snackbarMessage = _snackbarMessage.asStateFlow()

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

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    fun signInWithCredential(onProfileNotAvailable: () -> Unit) {
        viewModelScope.launch {
            when (val result = signInWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignInWithCredentialUseCase.Result.Error -> {
                    _snackbarMessage.value = result.message
                }

                is SignInWithCredentialUseCase.Result.Success -> {
                    when (val result = checkProfileAvailableUseCase()) {
                        is CheckProfileAvailableUseCase.Result.Error -> {}
                        is CheckProfileAvailableUseCase.Result.Success -> {
                            when (result.user) {
                                is UserModel -> {
                                    supabaseService.setIsUserSignedIn(true)
                                }

                                null -> {
                                    onProfileNotAvailable()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun signUpWithCredential(
        onSignedUp: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signUpWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignUpWithCredentialUseCase.Result.Error -> {
                    _snackbarMessage.value = result.message
                }

                is SignUpWithCredentialUseCase.Result.Success -> {
                    onSignedUp()
                }
            }
        }
    }

    // FIXME: WTF args?
    fun onSignInWithGoogle(
        signInResult: NativeSignInResult,
        onSignedUp: () -> Unit,
    ) {
        viewModelScope.launch {
            when (val result = signInWithGoogleUseCase(signInResult)) {
                is SignInWithGoogleUseCase.Result.Error -> {
                    _snackbarMessage.value = result.message
                }

                is SignInWithGoogleUseCase.Result.Success -> {
                    if (result.googleNavigateResultModel.isUserRegisterProfile) {
                        supabaseService.setIsUserSignedIn(true)
                    } else {
                        onSignedUp()
                    }
                }
            }
        }
    }
}
