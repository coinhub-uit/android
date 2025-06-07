package com.coinhub.android.domain.repositories

import com.coinhub.android.common.enums.ThemeModeEnum
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface PreferenceDataStore {
    suspend fun saveAccessToken(value: String)

    suspend fun getAccessToken(): String?

    suspend fun saveThemeMode(themeMode: ThemeModeEnum)
    
    suspend fun getThemeMode(): ThemeModeEnum

    suspend fun getTotalPrincipal(): Flow<BigInteger?>

    suspend fun saveTotalPrincipal(value: BigInteger)

    suspend fun getTotalInterest(): Flow<BigInteger?>

    suspend fun saveTotalInterest(value: BigInteger)
    
    suspend fun clearPreferences()
}
