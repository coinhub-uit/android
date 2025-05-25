package com.coinhub.android.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    suspend operator fun invoke(email: String): Result {
        return withContext(Dispatchers.Default) {
            if (email.isBlank()) {
                Result.Error(message = "Email cannot be empty")
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Result.Error(message = "Invalid email format")
            } else {
                Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}