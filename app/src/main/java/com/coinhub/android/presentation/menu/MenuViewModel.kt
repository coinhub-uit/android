package com.coinhub.android.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val supabaseService: SupabaseService,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    fun onSignOut() {
        viewModelScope.launch {
            supabaseService.signOut()
        }
    }

    fun fetch() {
        viewModelScope.launch {
            when (val result = userRepository.getUserById(authRepository.getCurrentUserId())) {
                is UserModel -> {
                    _user.value = result
                }

                null -> {
                    _toastMessage.emit("Failed to fetch user data")
                }
            }
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            // TODO: @NTGNguyen Logic to delete the user account
        }
    }
}
