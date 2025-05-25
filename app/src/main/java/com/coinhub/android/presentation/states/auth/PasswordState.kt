package com.coinhub.android.presentation.states.auth

data class PasswordState(
    val password: String = "",
    val isValid: Boolean = true,
    val errorMessage: String? = null,
)
