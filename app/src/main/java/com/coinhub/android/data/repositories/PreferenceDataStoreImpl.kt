package com.coinhub.android.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.coinhub.android.CoinHubApplication.Companion.appContext
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl.Companion.MY_PREF_KEY
import com.coinhub.android.domain.repositories.PreferenceDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = MY_PREF_KEY
)

class PreferenceDataStoreImpl @Inject constructor() : PreferenceDataStore {

    private val context: Context = appContext()

    companion object {
        const val MY_PREF_KEY = "COINHUB_PREF_KEY"
    }

    private val ACCESS_TOKEN = stringPreferencesKey("access_token")

    override suspend fun saveAccessToken(value: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = value
        }
    }

    override suspend fun getAccessToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }.first()
    }

    override suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
    }
}