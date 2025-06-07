package com.coinhub.android.domain.use_cases

import com.coinhub.android.domain.models.GoogleNavigateResultModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceDataStore: PreferenceDataStore,
) {
    suspend operator fun invoke(result: NativeSignInResult): Result {
        return withContext(Dispatchers.IO) {
            when (result) {
                NativeSignInResult.ClosedByUser -> Result.Error("Closed by user")
                is NativeSignInResult.Error -> Result.Error(result.message)
                is NativeSignInResult.NetworkError -> Result.Error(result.message)
                NativeSignInResult.Success -> {
                    try {
                        val googleNavigateResult =
                            authRepository.getUserOnSignInWithGoogle(preferenceDataStore::saveAccessToken)
                        Result.Success(googleNavigateResult)
                    } catch (e: Exception) {
                        Result.Error(e.message ?: "Error during sign-in with Google")
                    }
                }
            }
        }
    }

    sealed class Result {
        data class Success(val googleNavigateResultModel: GoogleNavigateResultModel) : Result()
        data class Error(val message: String) : Result()
    }
}
