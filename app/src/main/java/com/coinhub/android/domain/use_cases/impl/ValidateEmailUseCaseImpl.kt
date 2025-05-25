package com.coinhub.android.domain.use_cases.impl

import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateEmailUseCaseImpl : ValidateEmailUseCase {
    override suspend fun invoke(input: ValidateEmailUseCase.Input): ValidateEmailUseCase.Output {
        return withContext(Dispatchers.Default) {
            val email = input.email

            if (email.isBlank()) {
                ValidateEmailUseCase.Output(isValid = false, errorMessage = "Email cannot be empty")
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                ValidateEmailUseCase.Output(isValid = false, errorMessage = "Invalid email format")
            } else {
                ValidateEmailUseCase.Output(isValid = true)
            }
        }
    }
}