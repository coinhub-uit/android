package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.utils.CurrencySymbol
import com.coinhub.android.utils.toVndFormat
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject

@ViewModelScoped
class ValidateAmountCreateTicketUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(amount: String, minimumAmount: Long, source: SourceModel?): Result {
        return withContext(defaultDispatcher) {
            if (amount.isEmpty()) {
                Result.Error("Source cannot be empty")
            } else if ((amount.toLongOrNull() ?: 0L) < minimumAmount) {
                Result.Error("Amount must be at least ${minimumAmount.toVndFormat(currencySymbol = CurrencySymbol.VND)}")
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
