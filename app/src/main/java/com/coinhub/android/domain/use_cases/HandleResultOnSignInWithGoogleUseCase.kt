package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.presentation.states.auth.AuthState
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import javax.inject.Inject

class HandleResultOnSignInWithGoogleUseCase @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) {
    suspend operator fun invoke(result: NativeSignInResult): AuthState<GoogleNavigateResult> {
        return when (result) {
            NativeSignInResult.ClosedByUser -> AuthState.Error("")
            is NativeSignInResult.Error -> AuthState.Error("")
            is NativeSignInResult.NetworkError -> AuthState.Error("")
            NativeSignInResult.Success -> {
                val googleNavigateResult = authRepositoryImpl.handleNavigateResult()
                AuthState.Success(googleNavigateResult)
            }
        }
    }
}