package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateSourceDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SourceApiService {
    @POST("sources")
    suspend fun createSource(@Body createSourceDto: CreateSourceDto): SourceModel

    @GET("sources/{id}")
    suspend fun getSourceById(@Path("id") sourceId: String): SourceModel?

    @DELETE("sources/{id}")
    suspend fun deleteSource(@Path("id") sourceId: String): SourceModel

    @GET("sources/{id}/tickets")
    suspend fun getSourceTickets(@Path("id") sourceId: String): List<TicketModel>
}