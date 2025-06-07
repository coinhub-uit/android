package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.AvailablePlanModel

interface PlanRepository {
    suspend fun getAvailablePlans(): List<AvailablePlanModel>
}
