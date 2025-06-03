package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.repositories.PaymentRepositoryImpl
import com.coinhub.android.di.IoDispatcher
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateTopUpUseCase @Inject constructor(
    private val paymentRepositoryImpl: PaymentRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createTopUpDto: CreateTopUpDto): Result {
        return withContext(ioDispatcher) {
            try {
                val topUp = paymentRepositoryImpl.createTopUp(createTopUpDto)
                Result.Success(topUp)
            } catch (e: Exception) {
                (Result.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    sealed class Result {
        data class Success(val createTopUpModel: CreateTopUpModel) : Result()
        data class Error(val message: String) : Result()
    }
}