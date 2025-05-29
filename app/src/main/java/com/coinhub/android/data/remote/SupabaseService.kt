package com.coinhub.android.data.remote

import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
import com.coinhub.android.utils.ACCESS_TOKEN_KEY
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SupabaseService @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
) {
    suspend fun signIn(email: String, password: String) {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signUp(email: String, password: String) {
        supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun getCurrentUserId(): String {
        val token = supabaseClient.auth.currentAccessTokenOrNull() ?: throw Exception("")
        return supabaseClient.auth.retrieveUser(token).id
    }

    suspend fun getUserIdWithToken(token: String): String {
        return supabaseClient.auth.retrieveUser(token).id
    }

    fun getToken(): String? {
        return supabaseClient.auth.currentAccessTokenOrNull()
    }

    suspend fun refreshSession() {
        supabaseClient.auth.refreshCurrentSession()
    }

    suspend fun observeAndSaveSession(saveToken: (String, String) -> Unit) {
        coroutineScope {
            supabaseClient.auth.sessionStatus.collect {
                run {
                    when (it) {
                        is SessionStatus.Authenticated -> {
                            saveToken(
                                ACCESS_TOKEN_KEY,
                                it.session.accessToken
                            )
                        }

                        SessionStatus.Initializing -> TODO()
                        is SessionStatus.NotAuthenticated -> TODO()
                        is SessionStatus.RefreshFailure -> TODO()
                    }
                }
            }
        }
    }

    suspend fun isUserSignedIn(): Boolean {
        try {
            val token = sharedPreferenceRepositoryImpl.getStringData(ACCESS_TOKEN_KEY)
            if (token.isNullOrEmpty()) {
                return false
            } else {
                getUserIdWithToken(token)
                refreshSession()
                val newToken = getToken()!!
                sharedPreferenceRepositoryImpl.saveStringData(ACCESS_TOKEN_KEY, newToken)
                return true
            }
        } catch (e: Exception) {
            return false
        }
    }
}