package com.coinhub.android.presentation.states.profile

data class FullNameState(
    val fullName: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
)
