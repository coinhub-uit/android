package com.coinhub.android.data.remote

import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.utils.ACCESS_TOKEN_KEY
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SupabaseService @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val sharedPreferenceRepositoryImpl: PreferenceDataStoreImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private val supabaseServiceScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    init {
        supabaseServiceScope.launch {
            checkUserSignedIn()
        }
    }

    private var _isUserSignedIn = MutableStateFlow<UserAppState>(UserAppState.LOADING)
    val isUserSignedIn = _isUserSignedIn.asStateFlow()

    fun setIsUserSignedIn(isSignedIn: UserAppState) {
        _isUserSignedIn.value = isSignedIn
    }

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

    fun signOut() {
        supabaseServiceScope.launch {
            try {
                supabaseClient.auth.signOut()
                sharedPreferenceRepositoryImpl.clearPreferences()
                _isUserSignedIn.value = UserAppState.NOT_SIGNED_IN
            } catch (e: Exception) {
                throw Exception("Failed to sign out: ${e.message}")
            }
            //TODO: Please clear all aches
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

                        SessionStatus.Initializing -> {}
                        is SessionStatus.NotAuthenticated -> {}
                        is SessionStatus.RefreshFailure -> {}
                    }
                }
            }
        }
    }

    private suspend fun checkUserSignedIn() {
        try {
            val token = sharedPreferenceRepositoryImpl.getAccessToken()
            if (token.isNullOrEmpty()) {
                _isUserSignedIn.value = UserAppState.NOT_SIGNED_IN
            } else {
                getUserIdWithToken(token)
                refreshSession()
                val newToken = getToken()!!
                sharedPreferenceRepositoryImpl.saveAccessToken(newToken)
                _isUserSignedIn.value = UserAppState.LOCKED
            }
        } catch (e: Exception) {
            _isUserSignedIn.value = UserAppState.NOT_SIGNED_IN
        }
    }

    enum class UserAppState {
        LOADING,
        NOT_SIGNED_IN,
        SIGNED_IN,
        LOCKED,
        FAILED
    }
}


