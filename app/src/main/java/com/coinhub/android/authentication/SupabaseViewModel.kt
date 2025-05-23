package com.coinhub.android.authentication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.coinhub.android.authentication.aata.network.ApiServerClient
import com.coinhub.android.authentication.data.network.SupabaseClient.client
import com.coinhub.android.authentication.utils.SharedPreferenceHelper
import com.coinhub.android.navigation.Home
import com.coinhub.android.navigation.Login
import com.coinhub.android.presentation.states.UserState
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.launch

class SupabaseViewModel() : ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState
    var isUserLoggedIn by mutableStateOf(false)
        private set

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

    private fun retrieveUserId(context: Context) {
        viewModelScope.launch {
            try {
                val accessToken = getToken(context);
                if (accessToken.isNullOrEmpty()) {
                    return@launch
                }
                _userId.value = client.auth.retrieveUser(accessToken).id;
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}")
            }
        }
    }

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                isUserLoggedIn = true
                client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Registered user successfully")
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    return@launch
                }
                ApiServerClient.setToken(token)
                navController.navigate(Home)
            } catch (e: Exception) {
                isUserLoggedIn = false
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun signIn(
        context: Context,
        userEmail: String,
        userPassword: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                isUserLoggedIn = true
                client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Signed in successfully")
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    return@launch
                }
                retrieveUserId(context)
                ApiServerClient.setToken(token)
                navController.navigate(Home)
            } catch (e: Exception) {
                isUserLoggedIn = false
                _userState.value = UserState.Error("${e.message}")
            }
        }
    }

    fun signOut(context: Context, navController: NavController) {
        val sharedPref = SharedPreferenceHelper(context)
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                isUserLoggedIn = true
                client.auth.signOut()
                sharedPref.clearPreferences()
                _userState.value = UserState.Success("Signed out successfully")
                retrieveUserId(context)
                navController.navigate(Login)
                ApiServerClient.unsetToken()
            } catch (e: Exception) {
                isUserLoggedIn = false
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
                    isUserLoggedIn = false
                } else {
                    client.auth.retrieveUser(token)
                    client.auth.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User is already signed in")
                    isUserLoggedIn = true
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("${e.message}")
                isUserLoggedIn = false
            }
        }
    }

    fun checkGoogleLoginStatus(
        context: Context,
        result: NativeSignInResult,
        navController: NavController
    ) {
        isUserLoggedIn = false
        when (result) {
            is NativeSignInResult.Success -> {
                saveToken(context)
                _userState.value = UserState.Success("Signed with google successfully")
                isUserLoggedIn = true
                navController.navigate(Home)
            }

            is NativeSignInResult.ClosedByUser -> {

            }

            is NativeSignInResult.Error -> {
                _userState.value = UserState.Error(result.message)
            }

            is NativeSignInResult.NetworkError -> {
                _userState.value = UserState.Error(result.message)
            }
        }
    }
}

