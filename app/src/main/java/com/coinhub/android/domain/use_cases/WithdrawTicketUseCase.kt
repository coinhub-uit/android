package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.di.IoDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@ViewModelScoped
class WithdrawTicketUseCase @Inject constructor(
    private val ticketApiService: TicketApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(ticketId: Int): Result {
        return withContext(ioDispatcher) {
            try {
                ticketApiService.withdrawTicket(ticketId)
                Result.Success
            } catch (e: HttpException) {
                Result.Error(e.message ?: "An error occurred while withdrawing the ticket")
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
