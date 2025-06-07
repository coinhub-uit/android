package com.coinhub.android.data.repositories

import android.util.Log
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.GoogleNavigateResultModel
import com.coinhub.android.domain.repositories.AuthRepository
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class AuthRepositoryImpl @Inject constructor(
    private val userRepository: UserRepositoryImpl,
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

    override suspend fun getUserOnSignInWithGoogle(
        saveToken: KSuspendFunction1<String, Unit>,
    ): GoogleNavigateResultModel {
        val token = supabaseService.getToken()!!
        saveToken(token)
        val userId = supabaseService.getCurrentUserId()
        val user = try {
            userRepository.getUserById(userId)
        } catch (e: Exception) {
            null
        }
        return GoogleNavigateResultModel(isUserRegisterProfile = user != null, userId = userId)
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

    override suspend fun getCurrentUserId(): String {
        return supabaseService.getCurrentUserId()
    }

    override suspend fun refreshSession() {
        supabaseService.refreshSession()
    }

    override suspend fun observeAndSaveToken(saveToken: (String, String) -> Unit) {
        supabaseService.observeAndSaveSession(saveToken)
    }

    //Which func not overridden just for testing purposes
    suspend fun logUserById(userId: String) {
        try {
            Log.d("TEST", "logUserById: ${userRepository.getUserById(userId)}")
        } catch (e: Exception) {
            Log.d("TEST", "logUserById: Error fetching user by ID: ${e.message}")
            throw e
        }
    }
}
