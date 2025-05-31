package com.coinhub.android.presentation.create_source

sealed class CreateSourceStates {
    data class SourceCheckState(
        val isValid: Boolean = true,
        val errorMessage: String? = null,
    ) : CreateSourceStates()
}