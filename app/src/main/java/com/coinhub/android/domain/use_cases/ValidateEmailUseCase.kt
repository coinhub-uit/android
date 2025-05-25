package com.coinhub.android.domain.use_cases

interface ValidateEmailUseCase : UseCase<ValidateEmailUseCase.Input, ValidateEmailUseCase.Output> {
    data class Input(val email: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}