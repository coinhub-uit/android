package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.data.repositories.PaymentRepositoryImpl
import com.coinhub.android.di.IoDispatcher
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopUpUseCase @Inject constructor(
    private val paymentRepositoryImpl: PaymentRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(topUpId: String): Flow<Result> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(paymentRepositoryImpl.getTopUpById(topUpId)))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }

    sealed class Result {
        data object Loading : Result()
        data class Success(val topUpModel: TopUpModel) : Result()
        data class Error(val message: String) : Result()
    }
}