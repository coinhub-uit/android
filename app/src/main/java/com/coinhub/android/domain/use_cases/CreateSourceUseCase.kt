package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateSourceDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.repositories.SourceRepositoryImpl
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateSourceUseCase @Inject constructor(
    private val sourceRepositoryImpl: SourceRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createSourceDto: CreateSourceDto): Result {
        return withContext(ioDispatcher) {
            try {
                Result.Success(sourceRepositoryImpl.createSource(createSourceDto))
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data object Loading : Result()
        data class Success(val source: SourceModel) : Result()
        data class Error(val message: String) : Result()
    }
}