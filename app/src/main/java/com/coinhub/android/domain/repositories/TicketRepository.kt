package com.coinhub.android.domain.repositories

import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel

interface TicketRepository {
    fun getSourceByTicketId(id: String): SourceModel?

    fun getTicketById(id: Int, refresh: Boolean): TicketModel
}
