package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteSourceUseCase @Inject constructor(
    private val sourceApiService: SourceApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(sourceId: String): Result {
        return withContext(ioDispatcher) {
            try {
                sourceApiService.deleteSource(sourceId)
                Result.Success("Source deleted successfully")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data class Success(val message: String) : Result()
        data class Error(val message: String) : Result()
    }
}