package com.coinhub.android.data.repository

import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.GoogleNavigateResult
import com.coinhub.android.data.models.User
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.repository.AuthRepository
import com.coinhub.android.utils.ACCESS_TOKEN_KEY
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction2

class AuthRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val supabaseService: SupabaseService,
) :
    AuthRepository {
    override suspend fun getUserOnSignInWithCredential(email: String, password: String): String {
        supabaseService.signIn(email, password)
        return supabaseService.getCurrentUserId()
    }

    override suspend fun getUserOnSignUpWithCredential(email: String, password: String): String {
        supabaseService.signUp(email, password)
        return supabaseService.getCurrentUserId()
    }

    override suspend fun registerProfile(createUserDto: CreateUserDto): User {
        return userApiService.registerProfile(createUserDto)
    }

    override suspend fun getUserOnSignInWithGoogle(
        saveToken: KSuspendFunction2<String, String, Unit>,
    ): GoogleNavigateResult {
        val token = supabaseService.getToken()!!
        saveToken(ACCESS_TOKEN_KEY, token)
        val userId = supabaseService.getCurrentUserId()
        val user = try {
            userApiService.getUserById(userId)
        } catch (e: Exception) {
            null
        }
        return GoogleNavigateResult(isUserRegisterProfile = user != null, userId = userId)
    }

    override suspend fun getToken(): String {
        val token = supabaseService.getToken()
        if (token.isNullOrEmpty()) {
            throw Exception("")
        }
        return token
    }

    override suspend fun getUserIdWithToken(token: String): String {
        return supabaseService.getUserIdWithToken(token)
    }

    override suspend fun refreshSession() {
        supabaseService.refreshSession()
    }

    override suspend fun observeAndSaveToken(saveToken: (String, String) -> Unit) {
        supabaseService.observeAndSaveSession(saveToken)
    }
}