package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class SignUpWithCredentialUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend operator fun invoke(email: String, password: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val userId: String =
                    authRepository.getUserOnSignUpWithCredential(email = email, password = password)
                val token = authRepository.getToken()
                // TODO: @NTGNguyen check this again. the token is String? and the saveAccessToken expects a String
                // Just add to suppress the warning for now
                if (token.isNullOrEmpty()) {
                    return@withContext Result.Error("Failed to retrieve access token")
                }
                preferenceDataStore.saveAccessToken(token)
                Result.Success(userId)
            } catch (e: Exception) {
                //TODO: handle exception?
                Result.Error(e.message ?: "")
            }
        }
    }

    sealed class Result {
        data class Success(val userId: String) : Result()
        data class Error(val message: String) : Result()
    }
}
