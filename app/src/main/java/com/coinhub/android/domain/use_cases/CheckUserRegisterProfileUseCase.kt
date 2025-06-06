package com.coinhub.android.domain.use_cases

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckUserRegisterProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Result {
        return withContext(ioDispatcher) {
            try {
                val userId = authRepository.getCurrentUserId()
                val user = userRepository.getUserById(userId)
                Log.d("CheckUSer", "invoke: djawkdlwa")
                Result.Success(user = user)
            } catch (e: Exception) {
                if (e is NotFoundException) {
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