package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.SettingModel

interface SettingRepository {
    suspend fun getSettings(): SettingModel?
}