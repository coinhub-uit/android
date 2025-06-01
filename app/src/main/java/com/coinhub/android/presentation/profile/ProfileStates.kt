package com.coinhub.android.presentation.profile

sealed class ProfileStates {
    sealed class ProfileStatus : ProfileStates() {
        data object Ready : ProfileStates()

        data object Success : ProfileStates()

        data class Error(val errorMessage: String?) : ProfileStates()
    }

    data class FullNameCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates()

    data class BirthDateCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates()

    data class CitizenIdCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates()
}