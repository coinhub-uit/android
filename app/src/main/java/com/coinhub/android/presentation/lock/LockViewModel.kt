package com.coinhub.android.presentation.lock

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor() : ViewModel() {
    // A simple PIN validation (for demo purposes - typically would check against stored value)
    // In a real app, you'd validate against a securely stored PIN
    private val correctPin = "1234"

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getBiometricPromptCallback(context: Context, executor: Executor): () -> Unit {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .build()

        val biometricPrompt = BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    unlock()
                }
            }
        )

        return { biometricPrompt.authenticate(promptInfo) }
    }

    fun setPin(value: String) {
        _pin.value = value
    }

    fun unlock() {
        viewModelScope.launch {
            if (_pin.value != correctPin) {
                return@launch
            }
            _isLoading.value = true
            // Simulate some authentication delay
            delay(500)
            onUnlocked()
            Log.d("HEHEHEHE", "unlock:")
            _isLoading.value = false
        }
    }

    // This would be called after successful authentication
    private fun onUnlocked() {
        // Empty for now, will be implemented later
        // Would typically navigate to the main screen or perform some action
    }
}
