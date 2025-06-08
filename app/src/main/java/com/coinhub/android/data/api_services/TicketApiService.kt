package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.dtos.response.SourceResponseDto
import com.coinhub.android.data.dtos.response.TicketResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiService {
    @POST("tickets")
    suspend fun createTicket(@Body createTicketDto: CreateTicketRequestDto): TicketResponseDto

    @GET("tickets/{id}/source")
    suspend fun getSourceByTicketId(@Path("id") ticketId: String): SourceResponseDto?

    @GET("tickets/{id}/withdraw")
    suspend fun withdrawTicket(@Path("id") ticketId: Int)

    @GET("tickets/{id}")
    suspend fun getTicketById(@Path("id") ticketId: Int): TicketResponseDto
}