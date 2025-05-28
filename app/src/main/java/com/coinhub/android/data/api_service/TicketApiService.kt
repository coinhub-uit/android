package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateTicketDto
import com.coinhub.android.data.models.Ticket
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiService {
    @POST("tickets")
    fun createTicket(@Body createTicketDto: CreateTicketDto): Ticket

    @GET("tickets/{id}")
    fun withdrawTicket(@Path("id") ticketId: String)
}