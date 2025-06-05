package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.response.AvailablePlanResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface PlanApiService {
    @GET("available-plans")
    suspend fun getAvailablePlans(): Response<List<AvailablePlanResponseDto>>
}