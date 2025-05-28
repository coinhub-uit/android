package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
) {
    suspend operator fun invoke(email: String, password: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val userId: String =
                    authRepositoryImpl.getUserOnSignInWithCredential(email = email, password = password)
                val token = authRepositoryImpl.getToken()
                sharedPreferenceRepositoryImpl.saveStringData("accessToken", token)
                Result.Success(userId)
            } catch (e: Exception) {
                Result.Error(e.message ?: "")
            }
        }
    }

    sealed class Result {
        data class Success(val userId: String) : Result()
        data class Error(val message: String) : Result()
    }
}