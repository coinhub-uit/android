package com.coinhub.android.domain.repositories

import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel

interface SourceRepository {
    suspend fun getSourceById(id: String): SourceModel?
    suspend fun deleteSource(id: String): SourceModel
    suspend fun getSourceTickets(id: String): List<TicketModel>
}
