package com.coinhub.android.presentation.auth

sealed class AuthStates {
//    sealed class Status : AuthStates() {
//        data object Loading : Status()
//
//        data object Success : Status()
//
//        data class Error(val errorMessage: String) : Status()
//    }

    class EmailCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : AuthStates()

    class PasswordCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,

        ) : AuthStates()

    class ConfirmPasswordCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
        ) : AuthStates()
}