package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateTopUpRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.CreateTopUpModel
import com.coinhub.android.domain.repositories.PaymentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateTopUpUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createTopUpDto: CreateTopUpRequestDto): Result {
        return withContext(ioDispatcher) {
            try {
                val topUp = paymentRepository.createTopUp(createTopUpDto)
                    ?: return@withContext Result.Error("Top-up creation failed")
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