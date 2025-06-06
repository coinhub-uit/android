package com.coinhub.android.data.repositories

import com.coinhub.android.common.toSourceModel
import com.coinhub.android.common.toTicketModel
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketApiService: TicketApiService,
) : TicketRepository {

    private var ticketModel: TicketModel? = null
    private var oldTicketId: Int? = null

    override fun getSourceByTicketId(id: String): SourceModel? {
        return try {
            ticketApiService.getSourceByTicketId(id)?.toSourceModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTicketById(id: Int, refresh: Boolean): TicketModel {
        if (refresh || ticketModel == null || oldTicketId != id) {
            try {
                ticketModel = ticketApiService.getTicketById(id).toTicketModel()
                oldTicketId = id
            } catch (e: Exception) {
                throw e
            }
        }
        return ticketModel!!
    }
}
