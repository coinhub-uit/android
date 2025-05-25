package com.coinhub.android.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {
    suspend operator fun invoke(confirmPassword: String, password: String): Result {
        return withContext(Dispatchers.Default) {
            when {
                confirmPassword.isEmpty() -> Result.Error("Confirm password cannot be empty")
                confirmPassword.length < 4 -> Result.Error("Confirm password must be at least 4 characters long")
                confirmPassword != password -> Result.Error("Confirm password does not match the original password")
                else -> Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}