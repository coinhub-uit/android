package com.coinhub.android.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
//    private val repository: ProfileRepository
) {
    suspend operator fun invoke(fullName: String, birthDateInMillis: Long, citizenId: String, address: String?): Flow<Result> = flow {
        try {
            emit(Result.Loading)

//            val profile = Profile(
//                fullName = fullName,
//                birthDate = birthDate,
//                citizenId = citizenId,
//                address = address
//            )

//            repository.createProfile(profile)
            emit(Result.Success)
        } catch (e: Exception) { // TODO: Catch specific exceptions
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    sealed class Result {
        data object Loading : Result()
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
