package com.coinhub.android.domain.use_cases

interface ValidateConfirmPasswordUseCase : UseCase<ValidateConfirmPasswordUseCase.Input, ValidateConfirmPasswordUseCase.Output> {
    data class Input(val confirmPassword: String, val password: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}