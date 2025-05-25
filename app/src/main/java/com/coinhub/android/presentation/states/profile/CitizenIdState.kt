package com.kevinnitro.coinhub.presentation.profile.state

data class CitizenIdState(
    val citizenId: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
)
