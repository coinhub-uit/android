package com.coinhub.android.data.api_services

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AiChatApiService {
    @POST("ai-chat")
    suspend fun send(@Body aiChatMessage: Unit) // TODO: for now

    @GET("ai-chat")
    suspend fun getSession()

    @DELETE("ai-chat")
    suspend fun deleteSession()
}