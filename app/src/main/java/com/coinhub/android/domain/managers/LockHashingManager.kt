package com.coinhub.android.domain.managers

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.coinhub.android.domain.repositories.PreferenceDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LockHashingManager @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend fun save(pin: String) {
        val hashedPassword = BCrypt.withDefaults().hashToString(12, pin.toCharArray()) ?: return
        preferenceDataStore.saveLockPin(hashedPassword)
    }

    suspend fun check(pin: String): BCrypt.Result? {
        val hashedPassword = preferenceDataStore.getLockPin()
        val result = BCrypt.verifyer().verify(pin.toCharArray(), hashedPassword?.toCharArray())
        return result
    }
}