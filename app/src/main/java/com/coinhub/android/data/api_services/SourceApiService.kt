package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateSourceDto
import com.coinhub.android.data.dtos.response.SourceDto
import com.coinhub.android.data.dtos.response.TicketDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SourceApiService {
    @POST("sources")
    suspend fun createSource(@Body createSourceDto: CreateSourceDto): SourceDto

    @GET("sources/{id}")
    suspend fun getSourceById(@Path("id") sourceId: String): SourceDto?

    @DELETE("sources/{id}")
    suspend fun deleteSource(@Path("id") sourceId: String): SourceDto

    @GET("sources/{id}/tickets")
    suspend fun getSourceTickets(@Path("id") sourceId: String): List<TicketDto>
}