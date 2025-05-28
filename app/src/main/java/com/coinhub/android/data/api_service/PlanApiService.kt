package com.coinhub.android.data.api_service

import com.coinhub.android.data.models.AvailablePlan
import retrofit2.http.GET

interface PlanApiService {
    @GET("available-plans")
    suspend fun getAvailablePlans(): List<AvailablePlan>
}