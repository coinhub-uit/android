package com.coinhub.android.presentation.states.profile

data class BirthDateState(
    val birthDateInMillis: Long,
    val birthDateInString: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
)
