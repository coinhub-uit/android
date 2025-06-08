package com.coinhub.android.data.repositories

import com.coinhub.android.common.toSourceModel
import com.coinhub.android.common.toTicketModel
import com.coinhub.android.common.toUserModel
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.SourceRepository
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor(
    private val sourceApiService: SourceApiService,
) : SourceRepository {

    override suspend fun getSourceById(id: String): SourceModel? {
        return try {
            sourceApiService.getSourceById(id)?.toSourceModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteSource(id: String): SourceModel {
        return try {
            sourceApiService.deleteSource(id).toSourceModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSourceTickets(id: String): List<TicketModel> {
        return try {
            sourceApiService.getSourceTickets(id).map {
                it.toTicketModel()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSourceUser(id: String): UserModel? {
        return try {
            sourceApiService.getSourceUser(id).toUserModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
