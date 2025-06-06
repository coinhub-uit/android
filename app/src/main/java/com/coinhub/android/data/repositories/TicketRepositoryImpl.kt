package com.coinhub.android.data.repositories

import com.coinhub.android.common.toSourceModel
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.domain.repositories.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketApiService: TicketApiService,
) : TicketRepository {
    override fun getSourceByTicketId(id: String): SourceModel? {
        return try {
            ticketApiService.getSourceByTicketId(id)?.toSourceModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
