package com.coinhub.android.domain.repositories

import com.coinhub.android.data.models.TopUpModel

interface TopUpRepository {
    suspend fun getTopUp(
        sourceId: String
    ): TopUpModel
}
