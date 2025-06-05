package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.dtos.response.SourceResponseDto
import com.coinhub.android.data.dtos.response.TicketResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiService {
    @POST("tickets")
    fun createTicket(@Body createTicketDto: CreateTicketRequestDto): Response<TicketResponseDto>

    @GET("tickets/{id}/source")
    fun getSourceByTicketId(@Path("id") ticketId: String): Response<SourceResponseDto?>

    @GET("tickets/{id}")
    fun withdrawTicket(@Path("id") ticketId: String)
}