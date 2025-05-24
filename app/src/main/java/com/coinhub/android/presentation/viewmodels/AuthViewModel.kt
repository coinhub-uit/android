package com.coinhub.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.coinhub.android.utils.validateEmail as _validateEmail

const val PASSWORD_MIN_LENGTH = 4

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordError = MutableStateFlow(false)
    val isPasswordError: StateFlow<Boolean> = _isPasswordError.asStateFlow()

    private val _supportingPasswordText = MutableStateFlow("")
    val supportingPasswordText: StateFlow<String> = _supportingPasswordText.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isConfirmPasswordError = MutableStateFlow(false)
    val isConfirmPasswordError: StateFlow<Boolean> = _isConfirmPasswordError.asStateFlow()

    private val _supportingConfirmPasswordText = MutableStateFlow("")
    val supportingConfirmPasswordText: StateFlow<String> = _supportingConfirmPasswordText.asStateFlow()

    fun setSignUp(isSignUp: Boolean) {
        _isSignUp.value = isSignUp
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setIsEmailError(isError: Boolean) {
        _isEmailError.value = isError
    }

    fun validateEmail(email: String) {
        _isEmailError.value = _validateEmail(email)
    }

    // TODO: This is not optimise? setting 2 state at the same time
    fun setPassword(password: String) {
        _password.value = password
        _supportingPasswordText.value = ""
    }

    fun validatePassword() {
        if (_password.value.length < PASSWORD_MIN_LENGTH) {
            _supportingPasswordText.value = "Password must be at least $PASSWORD_MIN_LENGTH characters"
        } else {
            _supportingPasswordText.value = ""
        }
    }

    fun setIsPasswordError(isError: Boolean) {
        _isPasswordError.value = isError
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _supportingConfirmPasswordText.value = ""
    }

    fun validateConfirmPassword() {
        if (_confirmPassword.value != _password.value) {
            _supportingConfirmPasswordText.value = "Confirm password does not match"
        } else {
            _supportingConfirmPasswordText.value = ""
        }
    }

    fun setIsConfirmPasswordError(isError: Boolean) {
        _isConfirmPasswordError.value = isError
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
