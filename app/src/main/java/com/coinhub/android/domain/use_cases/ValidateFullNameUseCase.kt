package com.coinhub.android.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateFullNameUseCase @Inject constructor() {
    suspend operator fun invoke(fullName: String): Result {
        return withContext(Dispatchers.Default) {
            when {
                fullName.isEmpty() -> Result.Error("Full name cannot be empty")
                fullName.length < 2 -> Result.Error("Full name must be at least 2 characters long")
                !fullName.all { it.isLetter() || it.isWhitespace() } -> Result.Error("Full name can only contain letters and spaces")
                else -> Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
