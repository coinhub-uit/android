package com.coinhub.android.domain.repository

import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.models.User

interface AuthRepository {
    suspend fun signInWithCredential(email: String, password: String): String

    suspend fun signUpWithCredential(email: String, password: String): String

    suspend fun registerProfile(createUserDto: CreateUserDto): User

    suspend fun handleNavigateResult(): GoogleNavigateResult
}