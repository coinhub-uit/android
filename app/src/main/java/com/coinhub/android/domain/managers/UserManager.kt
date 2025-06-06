package com.coinhub.android.domain.managers

import android.util.Log
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CheckUserRegisterProfileUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserManager @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userRepository: UserRepository,
    private val checkUserRegisterProfileUseCase: CheckUserRegisterProfileUseCase,
) {
    val userState get() = UserAppState.LOCKED

    private var _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    suspend fun checkUserRegistered(): ProfileAvailableState {
        if (_user.value != null) {
            return ProfileAvailableState.Success(true)
        }
        when (val result = checkUserRegisterProfileUseCase()) {
            is CheckUserRegisterProfileUseCase.Result.Error -> {
                return ProfileAvailableState.Error(result.message)
            }

            is CheckUserRegisterProfileUseCase.Result.Success -> {
                Log.d("heelpo", "checkUserRegistered: ")
                if (result.user != null) {
                    _user.value = result.user
                    return ProfileAvailableState.Success(true)
                }
                return ProfileAvailableState.Success(false)
            }
        }
    }

    suspend fun reloadUser() {
        try {
            val userId = supabaseClient.auth.currentUserOrNull()?.id ?: return
            _user.value = userRepository.getUserById(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    fun clear() {
        _user.value = null
    }

    sealed class ProfileAvailableState {
        data class Success(val profileAvailable: Boolean) : ProfileAvailableState()
        data class Error(val message: String) : ProfileAvailableState()
    }
}

enum class UserAppState {
    LOADING,
    NOT_SIGNED_IN,
    SIGNED_IN,
    LOCKED,
    FAILED
}
