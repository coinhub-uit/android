package com.coinhub.android.domain.managers

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class LockManager {
    // WARN: Just a simple PIN validation for demo purposes.
    private val correctPin = "1234"

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
                            // CAll unlock method on success
                            Log.d("LockManager", "Authentication succeeded")
                        }
                    })
                return { biometricPrompt.authenticate(promptInfo) }
            }

            else -> {
                return null
            }
        }
    }

    fun unlock(pin: String) {
        if (validatePin(pin)) {
            Log.d("LockManager", "Unlock successful")
        } else {
            Log.d("LockManager", "Unlock failed")
        }
    }

    private fun validatePin(pin: String): Boolean {
        return pin == correctPin
    }
}