package com.coinhub.android.domain.repositories

interface PreferenceDataStore {
    suspend fun saveAccessToken(value: String)

    suspend fun getAccessToken(): String?

    suspend fun saveTempSourceId(value: String)

    suspend fun getTempSourceId(): String?

    suspend fun clearPreferences()
}