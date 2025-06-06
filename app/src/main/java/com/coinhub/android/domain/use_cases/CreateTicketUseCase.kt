package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.TicketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(
    private val ticketRepository: TicketRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(createTicketRequestDto: CreateTicketRequestDto) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(ticketRepository.createTicket(createTicketRequestDto)))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred while creating the ticket"))
        }
    }.flowOn(ioDispatcher)

    sealed class Result {
        data object Loading : Result()
        data class Success(val ticketModel: TicketModel) : Result()
        data class Error(val message: String) : Result()
    }
}