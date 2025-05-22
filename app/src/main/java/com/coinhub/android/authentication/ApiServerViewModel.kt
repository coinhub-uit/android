package com.coinhub.android.authentication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.authentication.data.dtos.CreateUserDto
import com.coinhub.android.authentication.data.model.User
import com.coinhub.android.authentication.data.network.ApiServerClient
import kotlinx.coroutines.launch

class ApiServerViewModel : ViewModel() {
    private var _user = mutableStateOf<User?>(null)

    val user: State<User?> = _user

    fun getUserById(userId: String) {
        viewModelScope.launch {
            try {
                _user.value = ApiServerClient.apiService.getUserById(userId);
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}")
            }
        }
    }

    fun registerProfile(userProfile: CreateUserDto) {
        viewModelScope.launch {
            try {
                ApiServerClient.apiService.registerProfile(userProfile)
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}")
            }
        }
    }
}
