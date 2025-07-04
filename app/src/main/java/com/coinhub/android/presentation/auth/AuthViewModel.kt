package com.coinhub.android.presentation.auth

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.use_cases.CheckProfileAvailableUseCase
import com.coinhub.android.domain.use_cases.RegisterDeviceUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    val supabaseClient: SupabaseClient,
    val preferenceDataStore: PreferenceDataStore,
    @ApplicationContext private val context: Context,
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

    private val _toastMessage = MutableSharedFlow<String>()
    var toastMessage = _toastMessage.asSharedFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    @SuppressLint("HardwareIds")
    private val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

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

    fun signInWithCredential(onProfileNotAvailable: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            when (val result = signInWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignInWithCredentialUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is SignInWithCredentialUseCase.Result.Success -> {
                    when (val userProfile = checkProfileAvailableUseCase()) {
                        is CheckProfileAvailableUseCase.Result.Error -> {
                            _toastMessage.emit(userProfile.message)
                        }
                        is CheckProfileAvailableUseCase.Result.Success -> {
                            when (userProfile.user) {
                                is UserModel -> {
                                    registerDevice()
                                    if (preferenceDataStore.getLockPin()
                                            .isNullOrEmpty()
                                    ) supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SET_LOCKED_PIN)
                                    else supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SIGNED_IN)
                                }

                                null -> {
                                    onProfileNotAvailable()
                                }
                            }
                        }
                    }
                }
            }
            _isProcessing.value = false
        }
    }

    fun signUpWithCredential(
        onSignedUp: () -> Unit,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            when (val result = signUpWithCredentialUseCase(email = _email.value, password = _password.value)) {
                is SignUpWithCredentialUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is SignUpWithCredentialUseCase.Result.Success -> {
                    onSignedUp()
                }
            }
            _isProcessing.value = false
        }
    }

    // FIXME: WTF args?
    fun onSignInWithGoogle(
        signInResult: NativeSignInResult,
        onSignedUp: () -> Unit,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            when (val result = signInWithGoogleUseCase(signInResult)) {
                is SignInWithGoogleUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is SignInWithGoogleUseCase.Result.Success -> {
                    if (result.googleNavigateResultModel.isUserRegisterProfile) {
                        registerDevice()
                        if (preferenceDataStore.getLockPin().isNullOrEmpty()) supabaseService.setIsUserSignedIn(
                            SupabaseService.UserAppState.SET_LOCKED_PIN
                        )
                        else supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SIGNED_IN)
                    } else {
                        onSignedUp()
                    }
                }
            }
            _isProcessing.value = false
        }
    }

    private fun registerDevice() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
                viewModelScope.launch {
                    when (val result = registerDeviceUseCase(
                        CreateDeviceRequestDto(
                            fcmToken = it,
                            deviceId = deviceId,
                        )
                    )) {
                        is RegisterDeviceUseCase.Result.Success -> {
                            // Successfully registered device, you can handle the result if needed
                        }

                        is RegisterDeviceUseCase.Result.Error -> {
                            _toastMessage.emit(result.message)
                        }
                    }
                }
            }
    }
}
