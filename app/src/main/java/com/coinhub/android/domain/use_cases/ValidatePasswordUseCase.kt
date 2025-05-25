package com.coinhub.android.domain.use_cases

interface ValidatePasswordUseCase : UseCase<ValidatePasswordUseCase.Input, ValidatePasswordUseCase.Output> {
    data class Input(val password: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}