package com.coinhub.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.presentation.states.auth.ConfirmPasswordState
import com.coinhub.android.presentation.states.auth.EmailState
import com.coinhub.android.presentation.states.auth.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
) : ViewModel() {
    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _emailState = MutableStateFlow(EmailState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordState())
    val passwordState = _passwordState.asStateFlow()

    private val _confirmPasswordState = MutableStateFlow(ConfirmPasswordState())
    val confirmPasswordState = _confirmPasswordState.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(
        emailState,
        passwordState,
        confirmPasswordState,
        isSignUp
    ) { email, password, confirmPassword, isSignup ->
        email.errorMessage == null && password.errorMessage == null && (isSignup && confirmPassword.errorMessage == null)
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setIsSignUp(isSignUp: Boolean) {
        _isSignUp.value = isSignUp
    }

    fun onEmailChange(email: String) {
        viewModelScope.launch {
            val result = validateEmailUseCase(email)
            _emailState.update {
                it.copy(
                    email = email,
                    isValid = result is ValidateEmailUseCase.Result.Success,
                    errorMessage = if (result is ValidateEmailUseCase.Result.Error) result.message else null,
                )
            }
        }
    }

    fun onPasswordChange(password: String) {
        viewModelScope.launch {
            val result = validatePasswordUseCase(password)
            _passwordState.update {
                it.copy(
                    password = password,
                    isValid = result is ValidatePasswordUseCase.Result.Success,
                    errorMessage = if (result is ValidatePasswordUseCase.Result.Error) result.message else null,
                )
            }
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        viewModelScope.launch {
            val result = validateConfirmPasswordUseCase(
                _passwordState.value.password,
                confirmPassword
            )
            _confirmPasswordState.update {
                it.copy(
                    confirmPassword = confirmPassword,
                    isValid = result is ValidateConfirmPasswordUseCase.Result.Success,
                    errorMessage = if (result is ValidateConfirmPasswordUseCase.Result.Error) result.message else null,
                )
            }
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
