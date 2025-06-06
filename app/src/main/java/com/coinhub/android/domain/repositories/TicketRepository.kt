package com.coinhub.android.domain.repositories

import com.coinhub.android.data.models.SourceModel

interface TicketRepository {
    fun getSourceByTicketId(id: String): SourceModel?
}
