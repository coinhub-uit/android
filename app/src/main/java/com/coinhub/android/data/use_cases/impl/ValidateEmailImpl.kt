package com.coinhub.android.data.use_cases.impl

import com.coinhub.android.data.use_cases.ValidateEmail

class ValidateEmailImpl : ValidateEmail {
    override suspend fun execute(input: ValidateEmail.Input): ValidateEmail.Output {
        val email = input.email
        return if (email.isBlank()) {
            ValidateEmail.Output(isValid = false, errorMessage = "Email cannot be empty")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidateEmail.Output(isValid = false, errorMessage = "Invalid email format")
        } else {
            ValidateEmail.Output(isValid = true)
        }
    }
}