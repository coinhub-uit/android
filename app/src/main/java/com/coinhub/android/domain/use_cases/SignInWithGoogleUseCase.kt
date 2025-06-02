package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.GoogleNavigateResultModel
import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val preferenceDataStoreImpl: PreferenceDataStoreImpl,
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
                            authRepositoryImpl.getUserOnSignInWithGoogle(preferenceDataStoreImpl::saveAccessToken)
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
