package com.coinhub.android.data.repositories

import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.domain.repositories.TopUpRepository

class TopUpRepositoryImpl(): TopUpRepository {
    override suspend fun getTopUp(sourceId: String): TopUpModel {
        TODO("Not yet implemented")
    }
}