package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.AiChatDRequestDto
import com.coinhub.android.data.dtos.response.AiChatResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AiChatApiService {
    @POST("ai-chat")
    suspend fun send(@Body aiChatRequestDto: AiChatDRequestDto): Response<AiChatResponseDto>

    @GET("ai-chat")
    suspend fun getSession(): Response<List<AiChatResponseDto>>

    @DELETE("ai-chat")
    suspend fun deleteSession()
}