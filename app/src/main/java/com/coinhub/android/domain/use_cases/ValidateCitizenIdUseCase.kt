package com.coinhub.android.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateCitizenIdUseCase @Inject constructor() {
    suspend operator fun invoke(citizenId: String): Result {
        return withContext(Dispatchers.Default) {
            when {
                citizenId.isEmpty() -> Result.Error("Citizen ID cannot be empty")
                citizenId.length != 12 -> Result.Error("Citizen ID must be 12 characters long")
                !citizenId.all { it.isDigit() } -> Result.Error("Citizen ID must contain only digits")
                else -> Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
