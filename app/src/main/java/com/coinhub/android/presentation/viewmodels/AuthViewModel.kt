package com.coinhub.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userApiService: UserApiService
) : ViewModel() {
    private var _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun getUserById(userId: String) {
        viewModelScope.launch {
            try {
                _user.value = userApiService.getUserById(userId);
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}") // TODO: TAg/
            }
        }
    }

    fun registerProfile(userProfile: CreateUserDto) {
        viewModelScope.launch {
            try {
                userApiService.registerProfile(userProfile)
            } catch (e: Exception) {
                Log.d("TAG", "${e.message}") // TODO: tag??
            }
        }
    }
}
