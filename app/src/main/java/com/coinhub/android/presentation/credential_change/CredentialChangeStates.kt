package com.coinhub.android.presentation.credential_change

sealed interface CredentialChangeStates {
    class CurrentPasswordState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : CredentialChangeStates

    class NewPasswordState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : CredentialChangeStates

    class ConfirmPasswordState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : CredentialChangeStates
}
