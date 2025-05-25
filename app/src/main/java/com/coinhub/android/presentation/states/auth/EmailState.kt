package com.coinhub.android.presentation.states.auth

data class EmailState(
    val email: String = "",
    val isValid: Boolean = true,
    val errorMessage: String? = null,
)
