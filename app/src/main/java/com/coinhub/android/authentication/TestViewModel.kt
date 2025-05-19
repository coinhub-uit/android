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
import androidx.navigation.NavHostController
import com.coinhub.android.authentication.data.model.UserState
import kotlinx.coroutines.launch
import com.coinhub.android.authentication.data.network.SupabaseClient.client
import com.coinhub.android.authentication.utils.SharedPreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult

class TestViewModel(navigator: NavHostController) : ViewModel() {
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
                navController.navigate("home")
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
                navController.navigate("home")
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
                navController.navigate("login")
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
                navController.navigate("home")
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

