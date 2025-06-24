package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.DeviceModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.data.repositories.UserRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class RegisterDeviceUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createDeviceRequestDto: CreateDeviceRequestDto): Result {
        return withContext(ioDispatcher) {
            try {
                Result.Success(userRepository.registerDevice(authRepository.getCurrentUserId(), createDeviceRequestDto))
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data class Success(val deviceModel: DeviceModel) : Result()
        data class Error(val message: String) : Result()
    }
}
