package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class SignUpWithCredentialUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend operator fun invoke(email: String, password: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                authRepository.signUpWithCredential(email = email, password = password)
                Result.Success
            } catch (e: Exception) {
                Result.Error(e.message ?: "")
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
