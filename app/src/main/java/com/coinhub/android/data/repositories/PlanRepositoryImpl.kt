package com.coinhub.android.data.repositories

import com.coinhub.android.common.toAvailablePlanModel
import com.coinhub.android.data.api_services.PlanApiService
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.repositories.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planApiService: PlanApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PlanRepository {

    override suspend fun getAvailablePlans(): List<AvailablePlanModel>? {
        return withContext(ioDispatcher) {
            try {
                planApiService.getAvailablePlans().map {
                    it.toAvailablePlanModel()
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}
