package com.coinhub.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.presentation.states.auth.AuthConfirmPasswordState
import com.coinhub.android.presentation.states.auth.AuthEmailState
import com.coinhub.android.presentation.states.auth.AuthPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PASSWORD_MIN_LENGTH = 4

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
) : ViewModel() {
    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _emailState = MutableStateFlow(AuthEmailState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(AuthPasswordState())
    val passwordState = _passwordState.asStateFlow()

    private val _confirmPasswordState = MutableStateFlow(AuthConfirmPasswordState())
    val confirmPasswordState = _confirmPasswordState.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(
        emailState,
        passwordState,
        confirmPasswordState,
        isSignUp
    ) { email, password, confirmPassword, isSignup ->
        email.isValid && password.isValid && (isSignup && confirmPassword.isValid)
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setIsSignUp(isSignUp: Boolean) {
        _isSignUp.value = isSignUp
    }

    fun onEmailChange(email: String) {
        viewModelScope.launch {
            val result = validateEmailUseCase(ValidateEmailUseCase.Input(email))
            _emailState.value = AuthEmailState(
                email = email,
                isValid = result.isValid,
                errorMessage = result.errorMessage
            )
        }
    }

    fun onPasswordChange(password: String) {
        viewModelScope.launch {
            val result = validatePasswordUseCase(ValidatePasswordUseCase.Input(password))
            _passwordState.value = AuthPasswordState(
                password = password,
                isValid = result.isValid,
                errorMessage = result.errorMessage
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        viewModelScope.launch {
            val result = validateConfirmPasswordUseCase(
                ValidateConfirmPasswordUseCase.Input(
                    _passwordState.value.password,
                    confirmPassword
                )
            )
            _confirmPasswordState.value = AuthConfirmPasswordState(
                confirmPassword = confirmPassword,
                isValid = result.isValid,
                errorMessage = result.errorMessage
            )
        }
    }

    fun registerProfile() {
        viewModelScope.launch {
            try {
                // TODO: @NTGNguyen handle this. Take the email password bla bla
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}") // TODO: tag??
            }
        }
    }
}
