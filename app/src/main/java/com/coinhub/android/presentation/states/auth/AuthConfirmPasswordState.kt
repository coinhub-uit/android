package com.coinhub.android.presentation.states.auth

data class AuthConfirmPasswordState(
    val confirmPassword: String = "",
    val isValid: Boolean = true,
    val errorMessage: String? = null,
)
