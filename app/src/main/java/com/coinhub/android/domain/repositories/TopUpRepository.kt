package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.CreateTopUpDto

interface TopUpRepository {
    suspend fun createTopUp(
        createTopUpDto: CreateTopUpDto,
    ): String
}
