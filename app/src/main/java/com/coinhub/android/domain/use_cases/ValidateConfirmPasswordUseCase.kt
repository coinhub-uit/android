package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ValidateConfirmPasswordUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(confirmPassword: String, password: String): Result {
        return withContext(defaultDispatcher) {
            when {
                confirmPassword.isEmpty() -> Result.Error("Confirm password cannot be empty")
                confirmPassword.length < 6 -> Result.Error("Confirm password must be at least 6 characters long")
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
