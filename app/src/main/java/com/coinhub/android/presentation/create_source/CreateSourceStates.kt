package com.coinhub.android.presentation.create_source

sealed class CreateSourceStates {
    data class SourceCheckState(
        val isValid: Boolean = false,
        val errorMessage: String? = null,
    ) : CreateSourceStates()
}