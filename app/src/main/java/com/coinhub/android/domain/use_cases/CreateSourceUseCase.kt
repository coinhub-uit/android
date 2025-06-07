package com.coinhub.android.domain.use_cases

import com.coinhub.android.common.toSourceModel
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.dtos.request.CreateSourceRequestDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateSourceUseCase @Inject constructor(
    private val sourceApiService: SourceApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createSourceDto: CreateSourceRequestDto): Result {
        return withContext(ioDispatcher) {
            try {
                Result.Success(sourceApiService.createSource(createSourceDto).toSourceModel())
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data class Success(val source: SourceModel) : Result()
        data class Error(val message: String) : Result()
    }
}