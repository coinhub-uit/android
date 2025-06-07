package com.coinhub.android.data.remote

import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.models.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserService @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val supabaseClient: SupabaseClient,
) {
    private var _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    suspend fun getUser() {
        try {
            val userId = supabaseClient.auth.currentUserOrNull()!!.id
            _user.value = userRepository.getUserById(userId)
        } catch (e: Exception) {
            throw e
        }
    }
}