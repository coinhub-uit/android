package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserSourcesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(refresh: Boolean): Result {
        return withContext(ioDispatcher) {
            try {
                val userId = authRepository.getCurrentUserId()
                Result.Success(userRepository.getUserSources(userId, refresh))
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data class Success(val sources: List<SourceModel>) : Result()
        data class Error(val message: String) : Result()
    }
}