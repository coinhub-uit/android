package com.coinhub.android.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.models.User
import com.coinhub.android.navigation.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userApiService: UserApiService,
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun signIn(onSuccess : (User) -> Unit) {
        viewModelScope.launch {
            try {
                supabaseClient.auth.signInWith(Email) {
                    email = _email.value
                    password = _password.value
                }
                val accessToken = supabaseClient.auth.currentAccessTokenOrNull()!!
                val userId = supabaseClient.auth.retrieveUser(accessToken).id
                val user: User = userApiService.getUserById(userId)
                    ?: throw Exception("User not found") // Exception bruh
                onSuccess(user)
            } catch (e: Exception)
            {
                // TODO: Handle error
            }
        }
    }
}
