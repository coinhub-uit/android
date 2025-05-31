package com.coinhub.android.domain.repositories

interface SharedPreferenceRepository {
    suspend fun saveStringData(key: String, value: String)

    fun getStringData(key: String): String?

    suspend fun clearPreferences()
}