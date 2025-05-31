package com.coinhub.android.data.repositories

import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.dtos.CreateTicketDto
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketApiService: TicketApiService,
) : TicketRepository {

    override fun createTicket(dto: CreateTicketDto): TicketModel {
        return try {
            ticketApiService.createTicket(dto)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun withdrawTicket(id: String) {
        try {
            ticketApiService.withdrawTicket(id)
        } catch (e: Exception) {
            throw e
        }
    }
}
