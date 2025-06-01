package com.coinhub.android.data.repositories

import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.dtos.CreateSourceDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.SourceRepository
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor(
    private val sourceApiService: SourceApiService,
) : SourceRepository {

    override suspend fun createSource(dto: CreateSourceDto): SourceModel {
        return try {
            sourceApiService.createSource(dto)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSourceById(id: String): SourceModel? {
        return try {
            sourceApiService.getSourceById(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteSource(id: String): SourceModel {
        return try {
            sourceApiService.deleteSource(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSourceTickets(id: String): List<TicketModel> {
        return try {
            sourceApiService.getSourceTickets(id)
        } catch (e: Exception) {
            throw e
        }
    }
}
