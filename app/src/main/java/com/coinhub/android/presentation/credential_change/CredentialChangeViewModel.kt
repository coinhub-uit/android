package com.coinhub.android.presentation.credential_change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.use_cases.ChangeCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
class CredentialChangeViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val supabaseService: SupabaseService,
    private val changeCredentialUseCase: ChangeCredentialUseCase,
) : ViewModel() {

    private val _currentPassword = MutableStateFlow("")
    val currentPassword = _currentPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _showPasswords = MutableStateFlow(false)
    val showPasswords = _showPasswords.asStateFlow()

    @OptIn(FlowPreview::class)
    val currentPasswordState = _currentPassword.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validatePasswordUseCase(it)
        CredentialChangeStates.CurrentPasswordState(
            isValid = result is ValidatePasswordUseCase.Result.Success,
            errorMessage = if (result is ValidatePasswordUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CredentialChangeStates.CurrentPasswordState())

    @OptIn(FlowPreview::class)
    val newPasswordState = _newPassword.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validatePasswordUseCase(it)
        val isNotSameAsOld = it != _currentPassword.value
        CredentialChangeStates.NewPasswordState(
            isValid = result is ValidatePasswordUseCase.Result.Success && isNotSameAsOld,
            errorMessage = when {
                result is ValidatePasswordUseCase.Result.Error -> result.message
                !isNotSameAsOld -> "New password must be different from current password"
                else -> null
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CredentialChangeStates.NewPasswordState())

    @OptIn(FlowPreview::class)
    val confirmPasswordState = _confirmPassword.drop(1).debounce(DEBOUNCE_TYPING).map {
        val passwordsMatch = it == _newPassword.value
        CredentialChangeStates.ConfirmPasswordState(
            isValid = passwordsMatch,
            errorMessage = if (!passwordsMatch) "Passwords don't match" else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CredentialChangeStates.ConfirmPasswordState())

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    val isFormValid = combine(
        currentPasswordState,
        newPasswordState,
        confirmPasswordState,
    ) { currentState, newState, confirmState ->
        currentState.isValid && newState.isValid && confirmState.isValid &&
                _currentPassword.value.isNotEmpty() && _newPassword.value.isNotEmpty() && _confirmPassword.value.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun updateCurrentPassword(password: String) {
        _currentPassword.value = password
    }

    fun updateNewPassword(password: String) {
        _newPassword.value = password
    }

    fun updateConfirmPassword(password: String) {
        _confirmPassword.value = password
    }

    fun toggleShowPasswords() {
        _showPasswords.value = !_showPasswords.value
    }

    fun changeCredential() {
        viewModelScope.launch {
            when (val result = changeCredentialUseCase(_newPassword.value)) {
                is ChangeCredentialUseCase.Result.Error -> _toastMessage.emit(result.message)
                is ChangeCredentialUseCase.Result.Success -> _toastMessage.emit(result.message)
            }

        }
    }
}