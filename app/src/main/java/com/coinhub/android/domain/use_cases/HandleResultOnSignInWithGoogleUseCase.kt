package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.repository.AuthRepositoryImpl
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import javax.inject.Inject

class HandleResultOnSignInWithGoogleUseCase @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) {
    suspend operator fun invoke(result: NativeSignInResult): Result {
        return when (result) {
            NativeSignInResult.ClosedByUser -> Result.Error("")
            is NativeSignInResult.Error -> Result.Error("")
            is NativeSignInResult.NetworkError -> Result.Error("")
            NativeSignInResult.Success -> {
                try {
                    val googleNavigateResult = authRepositoryImpl.handleNavigateResult()
                    Result.Success(googleNavigateResult)
                } catch (e: Exception) {
                    Result.Error(e.message ?: "")
                }
            }
        }
    }

    sealed class Result {
        data class Success(val googleNavigateResult: GoogleNavigateResult) : Result()
        data class Error(val message: String) : Result()
    }
}