package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateTopUpRequestDto
import com.coinhub.android.data.dtos.request.TransferMoneyRequestDto
import com.coinhub.android.data.dtos.response.CreateTopUpResponseDto
import com.coinhub.android.data.dtos.response.TopUpResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {
    @POST("payments/transfer-money")
    suspend fun transferMoney(@Body transferMoneyDto: TransferMoneyRequestDto)

    @POST("payment/top-up")
    suspend fun createTopUp(@Body createVnPayDto: CreateTopUpRequestDto): CreateTopUpResponseDto

    @GET("payment/top-up/{id}")
    suspend fun getTopUpById(@Path("id") id: String): TopUpResponseDto?
}