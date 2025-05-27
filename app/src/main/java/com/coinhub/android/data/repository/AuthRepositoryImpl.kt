package com.coinhub.android.data.repository

import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.models.User
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val supabaseService: SupabaseService,
) :
    AuthRepository {
    override suspend fun signInWithCredential(email: String, password: String): String {
        return supabaseService.getUserIdOnSignIn(email, password)
    }

    override suspend fun signUpWithCredential(email: String, password: String): String {
        return supabaseService.getUserIdOnSignUp(email, password)
    }

    override suspend fun registerProfile(createUserDto: CreateUserDto): User {
        return userApiService.registerProfile(createUserDto)
    }

    override suspend fun handleNavigateResult(): GoogleNavigateResult {
        val userId = supabaseService.getCurrentUserId()
        return GoogleNavigateResult(isUserRegisterProfile = userApiService.getUserById(userId) != null, userId = userId)
    }
}