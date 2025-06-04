package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel

interface TicketRepository {
    fun createTicket(dto: CreateTicketRequestDto): TicketModel
    fun getSourceByTicketId(id: String): SourceModel?
    fun withdrawTicket(id: String)
}
