package com.coinhub.android.presentation.lock

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.managers.LockHashingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor(
    private val supabaseService: SupabaseService,
    private val lockHashingManager: LockHashingManager,
) : ViewModel() {
    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val promptInfo: BiometricPrompt.PromptInfo by lazy {
        BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .setTitle("Biometric Authentication").setSubtitle("Log in using your biometric credential").build()
    }

    fun getBiometricPromptCallback(context: Context): (() -> Unit)? {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(context)
                val biometricPrompt = BiometricPrompt(
                    context as FragmentActivity, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            unlock()
                        }
                    })
                return { biometricPrompt.authenticate(promptInfo) }
            }

            else -> {
                return null
            }
        }
    }

    fun setPin(value: String) {
        if (value.length > 4) return
        _pin.value = value
    }

    fun tryPinUnlock() {
        viewModelScope.launch {
            val checkHashResult = lockHashingManager.check(_pin.value) ?: return@launch
            if (checkHashResult.verified) {
                unlock()
            } else {
                _toastMessage.emit("Incorrect PIN")
            }
        }
    }

    private fun unlock() {
        supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SIGNED_IN)
    }
}
