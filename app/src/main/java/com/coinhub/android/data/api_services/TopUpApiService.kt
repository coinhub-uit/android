package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.CreateTopUpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface TopUpApiService {
    @POST("payment/create")
    suspend fun createTopUp(@Body createVnPayDto: CreateTopUpDto): String
}