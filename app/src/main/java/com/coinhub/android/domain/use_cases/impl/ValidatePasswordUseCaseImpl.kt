package com.coinhub.android.domain.use_cases.impl

import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidatePasswordUseCaseImpl : ValidatePasswordUseCase {
    override suspend operator fun invoke(input: ValidatePasswordUseCase.Input): ValidatePasswordUseCase.Output {
        return withContext(Dispatchers.Default) {
            val password = input.password

            when {
                password.isEmpty() -> ValidatePasswordUseCase.Output(
                    isValid = false, errorMessage = "Password cannot be empty"
                )

                password.length < 4 -> ValidatePasswordUseCase.Output(
                    isValid = false, errorMessage = "Password must be at least 4 characters long"
                )

                !password.any { it.isDigit() } -> ValidatePasswordUseCase.Output(
                    isValid = false, errorMessage = "Password must contain at least one digit"
                )

                !password.any { it.isUpperCase() } -> ValidatePasswordUseCase.Output(
                    isValid = false, errorMessage = "Password must contain at least one uppercase letter"
                )

                else -> ValidatePasswordUseCase.Output(isValid = true)
            }
        }
    }
}