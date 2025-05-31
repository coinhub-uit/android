package com.coinhub.android.data.api_services

import com.coinhub.android.data.models.AvailablePlanModel
import retrofit2.http.GET

interface PlanApiService {
    @GET("available-plans")
    suspend fun getAvailablePlans(): List<AvailablePlanModel>
}