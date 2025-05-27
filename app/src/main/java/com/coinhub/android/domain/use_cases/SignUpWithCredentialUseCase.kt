package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class SignUpWithCredentialUseCase @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): Result {
        return try {
            Result.Success(authRepositoryImpl.signUpWithCredential(email = email, password = password))
        } catch (e: Exception) {
            //TODO: handle exception?
            Result.Error(e.message ?: "")
        }
    }

    sealed class Result {
        data class Success(val userId: String) : Result()
        data class Error(val message: String) : Result()
    }
}