package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
import javax.inject.Inject

class CheckUserSignedInUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
) {
    suspend operator fun invoke(): Result {
        return try {
            val token = sharedPreferenceRepositoryImpl.getStringData("accessToken")
            if (token.isNullOrEmpty()) {
                Result.Success(isSignedIn = false)
            } else {
                authRepositoryImpl.getUserIdWithToken(token)
                authRepositoryImpl.refreshSession()
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