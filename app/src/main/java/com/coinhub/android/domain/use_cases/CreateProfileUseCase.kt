package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(fullName: String, birthDateInMillis: Long, citizenId: String, address: String?): Flow<Result> = flow {
            emit(Result.Loading)

        
            emit(Result.Success)
        }.catch {
            emit(Result.Error(it.message ?: "Unknown error occurred"))
        }
    }

//    catch (e: Exception) { // TODO: Catch specific exceptions
//            emit(Result.Error(e.message ?: "Unknown error occurred"))
//        }
    }

    sealed class Result {
        data object Loading : Result()
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
