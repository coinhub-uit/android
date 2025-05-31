package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.CreateTicketDto
import com.coinhub.android.data.models.TicketModel

interface TicketRepository {
    fun createTicket(dto: CreateTicketDto): TicketModel
    fun withdrawTicket(id: String)
}
