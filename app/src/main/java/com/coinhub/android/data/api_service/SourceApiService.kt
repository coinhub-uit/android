package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateSourceDto
import com.coinhub.android.data.models.Source
import com.coinhub.android.data.models.Ticket
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SourceApiService {
    @POST("sources")
    suspend fun createSource(@Body createSourceDto: CreateSourceDto): Source

    @GET("sources/{id}")
    suspend fun getSourceById(@Path("id") sourceId: String): Source?

    @DELETE("sources/{id}")
    suspend fun deleteSource(@Path("id") sourceId: String): Source

    @GET("sources/{id}/tickets")
    suspend fun getSourceTickets(@Path("id") sourceId: String): List<Ticket>
}