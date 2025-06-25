package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ValidateCitizenIdUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(citizenId: String): Result {
        return withContext(defaultDispatcher) {
            when {
                citizenId.isEmpty() -> Result.Error("Citizen ID cannot be empty")
                citizenId.length != 12 -> Result.Error("Citizen ID is ${citizenId.length}/12 characters long")
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
