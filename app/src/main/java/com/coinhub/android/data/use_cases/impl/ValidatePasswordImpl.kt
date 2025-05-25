package com.coinhub.android.data.use_cases.impl

import com.coinhub.android.data.use_cases.ValidatePassword

class ValidatePasswordImpl : ValidatePassword {
    override suspend fun execute(input: ValidatePassword.Input): ValidatePassword.Output {
        val password = input.password
        return when {
            password.isEmpty() -> ValidatePassword.Output(
                isValid = false,
                errorMessage = "Password cannot be empty"
            )
            password.length < 4 -> ValidatePassword.Output(
                isValid = false,
                errorMessage = "Password must be at least 4 characters long"
            )
            !password.any { it.isDigit() } -> ValidatePassword.Output(
                isValid = false,
                errorMessage = "Password must contain at least one digit"
            )
            !password.any { it.isUpperCase() } -> ValidatePassword.Output(
                isValid = false,
                errorMessage = "Password must contain at least one uppercase letter"
            )
            else -> ValidatePassword.Output(isValid = true)
        }
    }
}