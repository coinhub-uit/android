package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.dtos.request.UpdatePartialUserRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.utils.toLocalDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val userApiService: UserApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        userId: String,
        fullName: String?,
        birthDateInMillis: Long?,
        citizenId: String?,
        address: String?,
    ): Result {
        return withContext(ioDispatcher) {
            try {
                userApiService.updatePartialProfile(
                    userId,
                    UpdatePartialUserRequestDto(
                        fullName = fullName,
                        birthDate = birthDateInMillis?.toLocalDate().toString(),
                        citizenId = citizenId,
                        address = address
                    )
                )
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