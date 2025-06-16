package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.domain.repositories.PaymentRepository
import jakarta.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class GetTopUpUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
) {
    suspend operator fun invoke(topUpId: String): Result {
        return try {
            val topUp = paymentRepository.getTopUpById(topUpId) ?: return Result.Error("Top-up not found")
            Result.Success(topUp)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error occurred")
        }
    }

    sealed class Result {
        data class Success(val topUp: TopUpModel) : Result()
        data class Error(val message: String) : Result()
    }
}
