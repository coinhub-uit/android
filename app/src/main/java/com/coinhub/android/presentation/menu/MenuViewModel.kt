package com.coinhub.android.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    private val _sources = MutableStateFlow<List<SourceModel>>(emptyList())
    val sources = _sources.asStateFlow()

    private val _tickets = MutableStateFlow<List<TicketModel>>(emptyList())
    val ticket = _tickets.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun onSignOut() {
        viewModelScope.launch {
            supabaseService.signOut()
        }
    }

    fun fetch() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            listOf(
                async { _user.value = userRepository.getUserById(userId, false) },
                async { _sources.value = userRepository.getUserSources(userId, false) },
                async { _tickets.value = userRepository.getUserTickets(userId, false) }
            ).awaitAll()
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            // TODO: @NTGNguyen Logic to delete the user account
        }
    }
}
