package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HandleResultOnSignInWithGoogleUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
) {
    suspend operator fun invoke(result: NativeSignInResult): Result {
        return withContext(Dispatchers.IO) {
            when (result) {
                NativeSignInResult.ClosedByUser -> Result.Error("")
                is NativeSignInResult.Error -> Result.Error("")
                is NativeSignInResult.NetworkError -> Result.Error("")
                NativeSignInResult.Success -> {
                    try {
                        val googleNavigateResult = authRepositoryImpl.getUserOnSignInWithGoogle()
                        val token = authRepositoryImpl.getToken()
                        sharedPreferenceRepositoryImpl.saveStringData("accessToken", token)
                        Result.Success(googleNavigateResult)
                    } catch (e: Exception) {
                        Result.Error(e.message ?: "")
                    }
                }
            }
        }
    }

    sealed class Result {
        data class Success(val googleNavigateResult: GoogleNavigateResult) : Result()
        data class Error(val message: String) : Result()
    }
}