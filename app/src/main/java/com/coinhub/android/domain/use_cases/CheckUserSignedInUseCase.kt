package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import javax.inject.Inject

//WARN: Maybe dont use this use case, but rather check the token directly in the view model, get it directly from supabase service
class CheckUserSignedInUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val preferenceDataStoreImpl: PreferenceDataStoreImpl,
) {
    suspend operator fun invoke(): Result {
        return try {
            val token = preferenceDataStoreImpl.getAccessToken()
            if (token.isNullOrEmpty()) {
                Result.Success(isSignedIn = false)
            } else {
                authRepositoryImpl.getUserIdWithToken(token)
                authRepositoryImpl.refreshSession()
                val newToken = authRepositoryImpl.getToken()
                preferenceDataStoreImpl.saveAccessToken(newToken)
                Result.Success(isSignedIn = true)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "")
        }
    }

    sealed class Result {
        data class Success(val isSignedIn: Boolean) : Result()
        data class Error(val message: String) : Result()
    }
}