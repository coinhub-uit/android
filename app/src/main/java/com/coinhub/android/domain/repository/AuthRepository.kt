package com.coinhub.android.domain.repository

import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.models.User

interface AuthRepository {
    suspend fun getUserOnSignInWithCredential(email: String, password: String): String

    suspend fun getUserOnSignUpWithCredential(email: String, password: String): String

    suspend fun registerProfile(createUserDto: CreateUserDto): User

    suspend fun getUserOnSignInWithGoogle(): GoogleNavigateResult

    suspend fun getToken(): String?

    suspend fun getUserIdWithToken(token: String): String

    suspend fun refreshSession()
}