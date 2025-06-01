package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.CreateTicketDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiService {
    @POST("tickets")
    fun createTicket(@Body createTicketDto: CreateTicketDto): TicketModel

    @GET("tickets/{id}/source")
    fun getSourceByTicketId(@Path("id") ticketId: String): SourceModel?

    @GET("tickets/{id}")
    fun withdrawTicket(@Path("id") ticketId: String)
}