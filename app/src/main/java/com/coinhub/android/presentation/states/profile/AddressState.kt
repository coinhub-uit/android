package com.kevinnitro.coinhub.presentation.profile.state

data class AddressState(
    val value: String = "",
    val error: String? = null,
    val isValid: Boolean = true // Default to true since address is optional
)
