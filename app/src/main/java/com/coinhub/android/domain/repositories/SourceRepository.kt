package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateSourceRequestDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel

interface SourceRepository {
    suspend fun createSource(dto: CreateSourceRequestDto): SourceModel
    suspend fun getSourceById(id: String): SourceModel?
    suspend fun deleteSource(id: String): SourceModel
    suspend fun getSourceTickets(id: String): List<TicketModel>
}
