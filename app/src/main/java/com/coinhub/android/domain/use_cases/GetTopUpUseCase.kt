package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.PaymentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTopUpUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(topUpId: String): Flow<Result> = flow {
        emit(Result.Loading)
        try {
            val topUp = paymentRepository.getTopUpById(topUpId)
            if (topUp == null) {
                emit(Result.Error("Top-up not found"))
                return@flow
            }
            emit(Result.Success(topUp))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error occurred"))
        }
    }.flowOn(ioDispatcher)

    sealed class Result {
        data object Loading : Result()
        data class Success(val topUp: TopUpModel) : Result()
        data class Error(val message: String) : Result()
    }
}