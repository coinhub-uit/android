package com.coinhub.android.data.use_cases.impl

import com.coinhub.android.data.use_cases.ValidateConfirmPassword

class ValidateConfirmPasswordImpl : ValidateConfirmPassword {
    override suspend fun execute(input: ValidateConfirmPassword.Input): ValidateConfirmPassword.Output {
        val confirmPassword = input.confirmPassword
        val password = input.password
        return if (confirmPassword.isBlank()) {
            ValidateConfirmPassword.Output(
                isValid = false,
                errorMessage = "Confirm password cannot be empty"
            )
        } else if (confirmPassword != password) {
            ValidateConfirmPassword.Output(
                isValid = false,
                errorMessage = "Passwords do not match"
            )
        } else {
            ValidateConfirmPassword.Output(isValid = true)
        }
    }
}