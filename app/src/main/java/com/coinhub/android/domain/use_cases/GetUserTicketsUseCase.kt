package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserTicketsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: kotlinx.coroutines.CoroutineDispatcher,
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            val userId = authRepository.getCurrentUserId()
            emit(Result.Success(userRepository.getUserTickets(userId)))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred while fetching the ticket"))
        }
    }.flowOn(ioDispatcher)

    sealed class Result {
        data object Loading : Result()
        data class Success(val ticketModels: List<TicketModel>) : Result()
        data class Error(val message: String) : Result()
    }
}