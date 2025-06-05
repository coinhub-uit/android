package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.response.NotificationResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApiService {
    @GET("notifications/{id}")
    suspend fun getUserById(@Path("id") notificationId: String): NotificationResponseDto?
}
