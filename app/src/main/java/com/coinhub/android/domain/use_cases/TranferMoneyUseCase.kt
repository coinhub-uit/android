package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.dtos.request.TransferMoneyRequestDto
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransferMoneyUseCase @Inject constructor(
    private val paymentApiService: PaymentApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(transferMoneyRequestDto: TransferMoneyRequestDto): Result {
        return withContext(ioDispatcher) {
            try {
                paymentApiService.transferMoney(transferMoneyRequestDto)
                Result.Success
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}