package com.coinhub.android.domain.use_cases.impl

import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateConfirmPasswordUseCaseImpl : ValidateConfirmPasswordUseCase {
    override suspend operator fun invoke(input: ValidateConfirmPasswordUseCase.Input): ValidateConfirmPasswordUseCase.Output {
        return withContext(Dispatchers.Default) {
            val confirmPassword = input.confirmPassword
            val password = input.password

            if (confirmPassword.isBlank()) {
                ValidateConfirmPasswordUseCase.Output(
                    isValid = false,
                    errorMessage = "Confirm password cannot be empty"
                )
            } else if (confirmPassword != password) {
                ValidateConfirmPasswordUseCase.Output(
                    isValid = false,
                    errorMessage = "Passwords do not match"
                )
            } else {
                ValidateConfirmPasswordUseCase.Output(isValid = true)
            }
        }
    }
}