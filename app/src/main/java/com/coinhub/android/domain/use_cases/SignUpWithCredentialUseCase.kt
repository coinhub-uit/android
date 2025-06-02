package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpWithCredentialUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val preferenceDataStoreImpl: PreferenceDataStoreImpl,
) {
    suspend operator fun invoke(email: String, password: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val userId: String =
                    authRepositoryImpl.getUserOnSignUpWithCredential(email = email, password = password)
                val token = authRepositoryImpl.getToken()
                preferenceDataStoreImpl.saveAccessToken(token)
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