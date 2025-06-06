package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserTicketsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result {
        return try {
            val userId = authRepository.getCurrentUserId()
            val ticketModels = userRepository.getUserTickets(userId)
            Result.Success(ticketModels)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred while fetching the ticket")
        }
    }

    sealed class Result {
        data class Success(val ticketModels: List<TicketModel>) : Result()
        data class Error(val message: String) : Result()
    }
}