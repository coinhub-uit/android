package com.coinhub.android.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.coinhub.android.CoinHubApplication.Companion.appContext
import com.coinhub.android.domain.repository.SharedPreferenceRepository

class SharedPreferenceRepositoryImpl() : SharedPreferenceRepository {

    private val context: Context = appContext()

    companion object {
        const val MY_PREF_KEY = "COINHUB_PREF_KEY"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)

    override suspend fun saveStringData(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun getStringData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override suspend fun clearPreferences() {
        return sharedPreferences.edit { clear() }
    }
}