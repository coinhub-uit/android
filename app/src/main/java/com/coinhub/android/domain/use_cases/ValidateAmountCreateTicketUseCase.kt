package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.SourceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger

class ValidateAmountCreateTicketUseCase {
    suspend operator fun invoke(amount: String, minimumAmount: Long, source: SourceModel): Result {
        return withContext(Dispatchers.Default) {
            if (amount.isEmpty()) {
                Result.Error("Source cannot be empty")
            } else if ((amount.toLongOrNull() ?: 0L) < minimumAmount) {
                Result.Error("Amount must be at least $minimumAmount")
            } else if (source.balance < BigInteger(amount)) {
                Result.Error("Insufficient balance in selected source")
            } else {
                Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}