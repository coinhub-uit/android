package com.coinhub.android.presentation.create_profile

sealed class CreateProfileStates {
    sealed class CreateProfileStatus : CreateProfileStates() {
        data object Ready : CreateProfileStates()

        data object Success : CreateProfileStates()

        data class Error(val errorMessage: String?) : CreateProfileStates()
    }

    data class FullNameCheckState(
        val isValid: Boolean = false,
        val errorMessage: String? = null,
    ) : CreateProfileStates()

    data class BirthDateCheckState(
        val isValid: Boolean = false,
        val errorMessage: String? = null,
    ) : CreateProfileStates()

    data class CitizenIdCheckState(
        val isValid: Boolean = false,
        val errorMessage: String? = null,
    ) : CreateProfileStates()
}