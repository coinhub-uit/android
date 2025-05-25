package com.coinhub.android.data.use_cases

interface ValidatePassword : UseCase<ValidatePassword.Input, ValidatePassword.Output> {
    data class Input(val password: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}