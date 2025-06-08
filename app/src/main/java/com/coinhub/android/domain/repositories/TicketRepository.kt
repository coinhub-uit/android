package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel

interface TicketRepository {
    suspend fun getSourceByTicketId(id: String): SourceModel?

    suspend fun getTicketById(id: Int, refresh: Boolean): TicketModel
}
