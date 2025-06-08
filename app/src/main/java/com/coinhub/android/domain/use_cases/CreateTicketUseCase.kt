package com.coinhub.android.domain.use_cases

import com.coinhub.android.common.toTicketModel
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.TicketModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(
    private val ticketApiService: TicketApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(createTicketRequestDto: CreateTicketRequestDto): Result {
        return withContext(ioDispatcher) {
            try {
                Result.Success(ticketApiService.createTicket(createTicketRequestDto).toTicketModel())
            } catch (e: Exception) {
                Result.Error(e.message ?: "An error occurred while creating the ticket")
            }
        }
    }

    sealed class Result {
        data class Success(val ticketModel: TicketModel) : Result()
        data class Error(val message: String) : Result()
    }
}