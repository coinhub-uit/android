package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.TransferMoneyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApiService {
    @POST("payments/transfer-money")
    suspend fun transferMoney(@Body transferMoneyDto: TransferMoneyDto)
}