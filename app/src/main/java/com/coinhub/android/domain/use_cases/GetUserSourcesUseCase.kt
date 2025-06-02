package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserSourcesUseCase @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val authRepositoryImpl: AuthRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Result {
        return withContext(ioDispatcher) {
            try {
                val userId = authRepositoryImpl.getCurrentUserId()
                Result.Success(userRepositoryImpl.getUserSources(userId))
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