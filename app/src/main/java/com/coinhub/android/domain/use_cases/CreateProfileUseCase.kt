package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@ViewModelScoped
class CreateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend operator fun invoke(
        fullName: String,
        birthDateInMillis: Long,
        citizenId: String,
        address: String?,
        avatar: String?,
    ): Result {
        return withContext(ioDispatcher) {
            try {
                userRepository.registerProfile(
                    user = CreateUserRequestDto(
                        id = authRepository.getCurrentUserId(),
                        fullName = fullName,
                        birthDate = Date(birthDateInMillis),
                        citizenId = citizenId,
                        address = address,
                        avatar = avatar
                    )
                )
                preferenceDataStore.saveAccessToken(authRepository.getToken()!!)
                Result.Success
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
