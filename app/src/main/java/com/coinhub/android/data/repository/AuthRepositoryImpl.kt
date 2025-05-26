package com.coinhub.android.data.repository

import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.models.User
import com.coinhub.android.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val supabaseClient: SupabaseClient,
) :
    AuthRepository {
    override suspend fun signInWithCredential(email: String, password: String): String {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        val token: String = supabaseClient.auth.currentAccessTokenOrNull()!!
        val userId = supabaseClient.auth.retrieveUser(token).id
        return userId
    }

    override suspend fun signUpWithCredential(email: String, password: String): String {
        supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        val token: String = supabaseClient.auth.currentAccessTokenOrNull()!!
        return supabaseClient.auth.retrieveUser(token).id
    }

    override suspend fun registerProfile(createUserDto: CreateUserDto): User {
        return userApiService.registerProfile(createUserDto)
    }

    override suspend fun handleNavigateResult(): GoogleNavigateResult {
        val token: String = supabaseClient.auth.currentAccessTokenOrNull()!!
        val userId = supabaseClient.auth.retrieveUser(token).id
        return GoogleNavigateResult(isUserRegisterProfile = userApiService.getUserById(userId) != null, userId = userId)
    }
}