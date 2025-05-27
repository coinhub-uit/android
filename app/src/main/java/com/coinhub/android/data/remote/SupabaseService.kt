package com.coinhub.android.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class SupabaseService @Inject constructor(private val supabaseClient: SupabaseClient) {
    suspend fun getUserIdOnSignIn(email: String, password: String): String {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        return getCurrentUserId()
    }

    suspend fun getUserIdOnSignUp(email: String, password: String): String {
        supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        return getCurrentUserId()
    }

    suspend fun getCurrentUserId(): String {
        val token = supabaseClient.auth.currentAccessTokenOrNull() ?: throw Exception("")
        return supabaseClient.auth.retrieveUser(token).id
    }
}