package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel

interface SourceRepository {
    suspend fun getSourceById(id: String): SourceModel?
    suspend fun deleteSource(id: String): SourceModel
    suspend fun getSourceTickets(id: String): List<TicketModel>
    suspend fun getSourceUser(id: String): UserModel?
}
