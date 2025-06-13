package com.coinhub.android.domain.managers

import at.favre.lib.crypto.bcrypt.BCrypt
import com.coinhub.android.domain.repositories.PreferenceDataStore
import javax.inject.Inject

class LockHashingManager @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend fun save(pin: String) {
        val hashedPassword = BCrypt.withDefaults().hashToChar(12, pin.toCharArray())?.toString() ?: return
        preferenceDataStore.saveLockPin(hashedPassword)
    }

    suspend fun check(pin: String): BCrypt.Result? {
        val hashedPassword = preferenceDataStore.getLockPin()
        return BCrypt.verifyer().verify(pin.toCharArray(), hashedPassword)
    }
}