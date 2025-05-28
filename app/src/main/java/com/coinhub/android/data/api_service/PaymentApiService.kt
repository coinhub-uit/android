package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.TranferMoneyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApiService {
    @POST("payments/tranfer-money")
    suspend fun transferMoney(@Body tranferMoneyDto: TranferMoneyDto)
}