package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto
import com.coinhub.android.data.dtos.response.CreateTopUpResponseDto
import com.coinhub.android.data.dtos.response.TopUpDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {
    @POST("payments/transfer-money")
    suspend fun transferMoney(@Body transferMoneyDto: TransferMoneyDto)

    @POST("payment/top-up")
    suspend fun createTopUp(@Body createVnPayDto: CreateTopUpDto): CreateTopUpResponseDto

    @GET("payment/top-up/{id}")
    suspend fun getTopUpById(@Path("id") id: String): TopUpDto
}