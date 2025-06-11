package com.coinhub.android.presentation.auth

sealed interface AuthStates {
    class EmailCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : AuthStates

    class PasswordCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
        ) : AuthStates

    class ConfirmPasswordCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
        ) : AuthStates
}