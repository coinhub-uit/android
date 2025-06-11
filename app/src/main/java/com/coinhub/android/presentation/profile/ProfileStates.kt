package com.coinhub.android.presentation.profile

sealed interface ProfileStates {
    data class FullNameCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates

    data class BirthDateCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates

    data class CitizenIdCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : ProfileStates
}