package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidatePinUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(pin: String): Result {
        return withContext(defaultDispatcher) {
            if (pin.isBlank()) {
                Result.Error(message = "Pin cannot be empty")
            } else if (pin.length != 4 || !pin.all { it.isDigit() }) {
                Result.Error(message = "Pin must be a 4-digit number")
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