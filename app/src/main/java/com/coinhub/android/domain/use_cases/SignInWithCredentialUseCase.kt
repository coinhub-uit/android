package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.presentation.states.auth.AuthState
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): AuthState<String> {
        return try {
            AuthState.Success(authRepositoryImpl.signInWithCredential(email = email, password = password))
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "")
        }
    }
}