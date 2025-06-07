package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.GoogleNavigateResultModel
import kotlin.reflect.KSuspendFunction1

interface AuthRepository {
    suspend fun getUserOnSignInWithCredential(email: String, password: String): String

    suspend fun getUserOnSignUpWithCredential(email: String, password: String): String

    suspend fun getUserOnSignInWithGoogle(saveToken: KSuspendFunction1<String, Unit>): GoogleNavigateResultModel

    suspend fun getToken(): String?

    suspend fun getUserIdWithToken(token: String): String

    suspend fun getCurrentUserId(): String

    suspend fun refreshSession()

    suspend fun observeAndSaveToken(saveToken: (String, String) -> Unit)
}