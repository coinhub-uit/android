package com.coinhub.android.data.repositories

import com.coinhub.android.common.toSettingsModel
import com.coinhub.android.data.api_services.SettingApiService
import com.coinhub.android.domain.models.SettingModel
import com.coinhub.android.domain.repositories.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingsApiService: SettingApiService,
) : SettingRepository {
    override suspend fun getSettings(): SettingModel? {
        return try {
            settingsApiService.getSettings().toSettingsModel()
        } catch (e: Exception) {
            return null
        }
    }
}