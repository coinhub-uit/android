package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(
        fullName: String,
        birthDateInMillis: Long,
        citizenId: String,
        address: String?,
    ): Flow<Result> = flow {
        emit(Result.Loading)
        userRepository.registerProfile(
            user = CreateUserRequestDto(
                id = "", // FIXME: @NTGNguyen get id
                fullName = fullName,
                birthDate = Date(birthDateInMillis),
                citizenId = citizenId,
                address = address,
                avatar = null
            )
        )
        emit(Result.Success)
    }.catch {
        emit(Result.Error(it.message ?: "Unknown error occurred"))
    }.flowOn(ioDispatcher)

    sealed class Result {
        data object Loading : Result()
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}


