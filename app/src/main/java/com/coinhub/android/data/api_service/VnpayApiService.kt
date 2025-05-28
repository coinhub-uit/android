package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateTopUpDto
import retrofit2.http.POST

interface VnpayApiService {
    @POST("payment/vnpay/create")
    suspend fun createTopUpPayment(@Body createVnPayDto: CreateTopUpDto): String
}