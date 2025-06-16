package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase.Result
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

//WARN: Maybe don't use this use case, but rather check the token directly in the view model, get it directly from supabase service
@ViewModelScoped
class CheckUserSignedInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend operator fun invoke(): Result {
        return try {
            val token = preferenceDataStore.getAccessToken()
            if (token.isNullOrEmpty()) {
                Result.Success(isSignedIn = false)
            } else {
                authRepository.getUserIdWithToken(token)
                authRepository.refreshSession()
                val newToken = authRepository.getToken()
                // TODO: @NTGNguyen check this again. the token is String? and the saveAccessToken expects a String
                // Just add to suppress the warning for now
                if (newToken.isNullOrEmpty()) {
                    return Result.Error("Failed to retrieve access token")
                }
                preferenceDataStore.saveAccessToken(newToken)
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
