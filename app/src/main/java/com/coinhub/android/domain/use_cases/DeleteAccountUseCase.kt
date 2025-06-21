package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val userApiService: UserApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(userId: String): Result {
        return withContext(ioDispatcher) {
            try {
                userApiService.delete(userId)
                Result.Success("Account deleted successfully")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data class Success(val message: String) : Result()
        data class Error(val message: String) : Result()
    }
}