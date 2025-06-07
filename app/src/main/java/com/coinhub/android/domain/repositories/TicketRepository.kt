package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel

interface TicketRepository {
    fun getSourceByTicketId(id: String): SourceModel?

    fun getTicketById(id: Int, refresh: Boolean): TicketModel
}
