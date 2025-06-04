package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.response.SourceResponseDto
import com.coinhub.android.data.dtos.response.TicketResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SourceApiService {
    @POST("sources")
    suspend fun createSource(@Body createSourceDto: com.coinhub.android.data.dtos.request.CreateSourceRequestDto): SourceResponseDto

    @GET("sources/{id}")
    suspend fun getSourceById(@Path("id") sourceId: String): SourceResponseDto?

    @DELETE("sources/{id}")
    suspend fun deleteSource(@Path("id") sourceId: String): SourceResponseDto

    @GET("sources/{id}/tickets")
    suspend fun getSourceTickets(@Path("id") sourceId: String): List<TicketResponseDto>
}