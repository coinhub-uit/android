package com.coinhub.android.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.coinhub.android.common.enums.ThemeModeEnum
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl.Companion.MY_PREF_KEY
import com.coinhub.android.domain.repositories.PreferenceDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.math.BigInteger
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = MY_PREF_KEY
)

class PreferenceDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PreferenceDataStore {

    companion object {
        const val MY_PREF_KEY = "COINHUB_PREF_KEY"
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val TOTAL_PRINCIPAL = stringPreferencesKey("total_principal")
        private val TOTAL_INTEREST = stringPreferencesKey("total_interest")
        private val LOCK_PIN = stringPreferencesKey("lock_password")
    }

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

    override suspend fun saveThemeMode(themeMode: ThemeModeEnum) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.name
        }
    }

    override suspend fun getThemeMode(): ThemeModeEnum {
        return context.dataStore.data.map { preferences ->
            val themeModeString = preferences[THEME_MODE]
            when (themeModeString) {
                ThemeModeEnum.LIGHT.name -> ThemeModeEnum.LIGHT
                ThemeModeEnum.DARK.name -> ThemeModeEnum.DARK
                else -> ThemeModeEnum.SYSTEM
            }
        }.first()
    }

    override suspend fun getTotalPrincipal(): Flow<BigInteger?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOTAL_PRINCIPAL]?.toBigIntegerOrNull()
        }
    }

    override suspend fun saveTotalPrincipal(value: BigInteger) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_PRINCIPAL] = value.toString()
        }
    }

    override suspend fun getTotalInterest(): Flow<BigInteger?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOTAL_INTEREST]?.toBigIntegerOrNull()
        }
    }

    override suspend fun saveTotalInterest(value: BigInteger) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_INTEREST] = value.toString()
        }
    }

    override suspend fun saveLockPin(value: String) {
        context.dataStore.edit { preferences ->
            preferences[LOCK_PIN] = value
        }
    }

    override suspend fun getLockPin(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[LOCK_PIN]
        }.first()
    }

    override suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
    }
}