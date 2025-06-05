package com.coinhub.android.domain.managers

import android.content.res.Resources.NotFoundException
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.domain.repositories.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserManager @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val userRepository: UserRepository,
) {
    val userState get() = UserAppState.LOCKED

    private var _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    suspend fun checkUserRegistered(): Boolean {
        if (_user.value != null) {
            return true
        }
        try {
            val userId = supabaseClient.auth.currentUserOrNull()!!.id
            _user.value = userRepository.getUserById(userId)
            return true
        } catch (e: Exception) {
            if (e is NotFoundException)
                return false
            else
                throw e
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
}

enum class UserAppState {
    LOADING,
    NOT_SIGNED_IN,
    SIGNED_IN,
    LOCKED,
    FAILED
}
