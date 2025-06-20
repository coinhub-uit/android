package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class CheckProfileAvailableUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Result {
        return withContext(ioDispatcher) {
            try {
                val userId = authRepository.getCurrentUserId()
                val user = userRepository.getUserById(userId, true)
                Result.Success(user = user)
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    Result.Success(user = null)
                } else {
                    Result.Error(e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    sealed class Result {
        data class Success(val user: UserModel?) : Result()
        data class Error(val message: String) : Result()
    }
}
