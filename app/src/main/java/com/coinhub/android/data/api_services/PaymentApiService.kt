package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApiService {
    @POST("payments/transfer-money")
    suspend fun transferMoney(@Body transferMoneyDto: TransferMoneyDto)

    @POST("payment/top-up")
    suspend fun createTopUp(@Body createVnPayDto: CreateTopUpDto): String
}