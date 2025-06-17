package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class CheckSourceExistedUseCase @Inject constructor(
    private val sourceApiService: SourceApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(sourceId: String): Result {
        return withContext(ioDispatcher) {
            try {
                sourceApiService.getSourceUser(sourceId)
                Result.Success(
                    message = "Source with ID '$sourceId' exists."
                )
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    Result.NotFoundError(message = "Source with ID '$sourceId' does not exist.")
                } else {
                    Result.Error(message = "An error occurred: ${e.message()}")
                }
            } catch (e: Exception) {
                Result.Error(message = "An unexpected error occurred: ${e.message}")
            }
        }
    }

    sealed class Result {
        data class Success(val message: String) : Result()
        data class Error(val message: String) : Result()
        data class NotFoundError(val message: String) : Result()
    }
}