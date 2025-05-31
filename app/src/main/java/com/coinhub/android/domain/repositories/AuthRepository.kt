package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResultModel
import com.coinhub.android.data.models.UserModel
import kotlin.reflect.KSuspendFunction2

interface AuthRepository {
    suspend fun getUserOnSignInWithCredential(email: String, password: String): String

    suspend fun getUserOnSignUpWithCredential(email: String, password: String): String

    suspend fun registerProfile(createUserDto: CreateUserDto): UserModel

    suspend fun getUserOnSignInWithGoogle(saveToken: KSuspendFunction2<String, String, Unit>): GoogleNavigateResultModel

    suspend fun getToken(): String?

    suspend fun getUserIdWithToken(token: String): String

    suspend fun refreshSession()

    suspend fun observeAndSaveToken(saveToken: (String, String) -> Unit)
}