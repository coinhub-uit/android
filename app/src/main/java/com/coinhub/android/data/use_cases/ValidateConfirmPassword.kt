package com.coinhub.android.data.use_cases

interface ValidateConfirmPassword : UseCase<ValidateConfirmPassword.Input, ValidateConfirmPassword.Output> {
    data class Input(val confirmPassword: String, val password: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}