package com.coinhub.android.presentation.set_pin

interface SetPinStates {
    data class PinCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    )
}