package com.coinhub.android.domain.repositories

import com.coinhub.android.common.enums.ThemeModeEnum

interface PreferenceDataStore {
    suspend fun saveAccessToken(value: String)

    suspend fun getAccessToken(): String?

    suspend fun saveTempSourceId(value: String)

    suspend fun getTempSourceId(): String?
    
    suspend fun saveThemeMode(themeMode: ThemeModeEnum)
    
    suspend fun getThemeMode(): ThemeModeEnum
    
    suspend fun clearPreferences()
}
