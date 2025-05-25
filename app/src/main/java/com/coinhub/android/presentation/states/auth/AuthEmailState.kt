package com.coinhub.android.presentation.states.auth

data class AuthEmailState(
    val email: String = "",
    val isValid: Boolean = true,
    val errorMessage: String? = null,
)
