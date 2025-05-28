package com.coinhub.android.domain.repository

interface SharedPreferenceRepository {
    suspend fun saveStringData(key: String, value: String): Unit

    suspend fun getStringData(key: String): String?

    suspend fun clearPreferences(): Unit
}