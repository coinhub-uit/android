package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeCredentialUseCase @Inject constructor(
    private val supabaseService: SupabaseService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        password: String,
        currentPassword: String,
    ): Result {
        return withContext(ioDispatcher) {
            try {
                supabaseService.changeCredential(password, currentPassword)
                Result.Success
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}