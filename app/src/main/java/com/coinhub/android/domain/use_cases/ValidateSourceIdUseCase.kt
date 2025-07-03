package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException

@ViewModelScoped
class ValidateSourceIdUseCase @Inject constructor(
    private val sourceApiService: SourceApiService,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(sourceId: String): Result {
        return withContext(defaultDispatcher) {
            if (sourceId.isEmpty()) {
                return@withContext Result.Error("Source cannot be empty")
            }

            if (sourceId.length > 20) {
                return@withContext Result.Error("Source cannot be longer than 20 characters")
            }

            if (sourceId.contains(" ")) {
                return@withContext Result.Error("Source cannot contain spaces")
            }

            try {
                sourceApiService.getSourceUser(sourceId)
                return@withContext Result.Error("Source with ID '$sourceId' already exists")
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    return@withContext Result.Success
                } else {
                    return@withContext Result.Error(message = "An error occurred: ${e.message()}")
                }
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
