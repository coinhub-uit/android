package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.response.SettingResponseDto
import retrofit2.http.GET

interface SettingApiService {
    @GET("setting")
    suspend fun getSettings(): SettingResponseDto
}