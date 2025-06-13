package com.coinhub.android.presentation.set_pin

interface SetPinStates {
    data class PinCheckState(
        val isValid: Boolean = false,
        val errorMessage: String? = null,
    )
}