package com.coinhub.android.domain.repositories

interface PreferenceDataStore {
    suspend fun saveAccessToken(value: String)

    suspend fun getAccessToken(): String?

    suspend fun clearPreferences()
}