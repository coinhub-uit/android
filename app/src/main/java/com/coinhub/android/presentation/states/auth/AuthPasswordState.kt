package com.coinhub.android.presentation.states.auth

data class AuthPasswordState(
    val password: String = "",
    val isValid: Boolean = true,
    val errorMessage: String? = null,
)
