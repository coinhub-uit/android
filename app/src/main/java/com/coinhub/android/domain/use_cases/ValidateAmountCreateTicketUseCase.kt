package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import com.coinhub.android.domain.models.SourceModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject

class ValidateAmountCreateTicketUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(amount: String, minimumAmount: Long, source: SourceModel?): Result {
        return withContext(defaultDispatcher) {
            if (amount.isEmpty()) {
                Result.Error("Source cannot be empty")
            } else if ((amount.toLongOrNull() ?: 0L) < minimumAmount) {
                Result.Error("Amount must be at least $minimumAmount")
            } else if (source != null && source.balance < BigInteger(amount)) {
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