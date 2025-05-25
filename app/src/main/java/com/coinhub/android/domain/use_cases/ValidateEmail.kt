package com.coinhub.android.data.use_cases

interface ValidateEmail : UseCase<ValidateEmail.Input, ValidateEmail.Output> {
    data class Input(val email: String)
    data class Output(val isValid: Boolean, val errorMessage: String? = null)
}