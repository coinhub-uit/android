package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(password: String): Result {
        return withContext(defaultDispatcher) {
            when {
                password.isEmpty() -> Result.Error("Password cannot be empty")
                password.length < 6 -> Result.Error("Password must be at least 6 characters long")
                !password.any { it.isDigit() } -> Result.Error("Password must contain at least one digit")
                !password.any { it.isUpperCase() } -> Result.Error("Password must contain at least one uppercase letter")
                !password.any { it.isLowerCase() } -> Result.Error("Password must contain at least one lowercase letter")
                else -> Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}