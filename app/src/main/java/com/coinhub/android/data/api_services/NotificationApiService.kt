package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.response.NotificationResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface NotificationApiService {
    @GET("notifications/{id}")
    suspend fun getUserById(@Path("id") notificationId: String): NotificationResponseDto?

    @OptIn(ExperimentalUuidApi::class)
    @GET("notifications/{id}/read")
    suspend fun readById(@Path("id") notificationId: Uuid)
}
