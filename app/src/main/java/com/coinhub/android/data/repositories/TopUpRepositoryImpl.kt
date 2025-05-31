package com.coinhub.android.data.repositories

import com.coinhub.android.data.api_services.TopUpApiService
import com.coinhub.android.data.dtos.CreateTopUpDto
import com.coinhub.android.domain.repositories.TopUpRepository
import jakarta.inject.Inject

class TopUpRepositoryImpl @Inject constructor(private val topUpApiService: TopUpApiService) : TopUpRepository {
    override suspend fun createTopUp(createTopUpDto: CreateTopUpDto): String {
        try {
            return topUpApiService.createTopUp(createTopUpDto)
        } catch (e: Exception) {
            throw e
        }
    }
}