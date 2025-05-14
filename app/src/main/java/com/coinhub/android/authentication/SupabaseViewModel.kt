package com.coinhub.android.authentication

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.authentication.data.model.UserState
import kotlinx.coroutines.launch
import com.coinhub.android.authentication.data.network.SupabaseClient.client
import com.coinhub.android.authentication.utils.SharedPreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult

class SupabaseViewModel : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = client.auth.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Registered user successfully")
            } catch (e: Exception) {
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun signIn(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Signed in successfully")
            } catch (e: Exception) {
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun signOut(context: Context) {
        val sharedPref = SharedPreferenceHelper(context)
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signOut()
                sharedPref.clearPreferences()
                _userState.value = UserState.Success("Signed out successfully")
            } catch (e: Exception) {
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun isUserSignedIn(
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("User is not signed in")
                } else {
                    client.auth.retrieveUser(token)
                    client.auth.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User is already signed in")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun checkGoogleLoginStatus(
        context: Context,
        result: NativeSignInResult
    ) {
        when (result) {
            is NativeSignInResult.Success -> {
                saveToken(context)
                _userState.value = UserState.Success("Signed with google successfully")
            }

            is NativeSignInResult.ClosedByUser -> {}
            is NativeSignInResult.Error -> {
                _userState.value = UserState.Error(result.message)
            }

            is NativeSignInResult.NetworkError -> {
                _userState.value = UserState.Error(result.message)
            }
        }
    }
}
