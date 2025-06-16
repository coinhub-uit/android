package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ValidateEmailUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(email: String): Result {
        return withContext(defaultDispatcher) {
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
