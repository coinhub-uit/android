package com.coinhub.android.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.utils.copyToClipboard
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
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    private val _sources = MutableStateFlow<List<SourceModel>>(emptyList())
    val sources = _sources.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
    }

    fun fetch() {
        if (_user.value != null) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            listOf(async { fetchSources() }, async { fetchUser() }).awaitAll()
            _isLoading.value = false
        }
    }

    private suspend fun fetchUser() {
        when (val result = userRepository.getUserById(authRepository.getCurrentUserId())) {
            is UserModel -> {
                _user.value = result
            }

            null -> {
                _toastMessage.emit("Failed to fetch user data")
            }
        }
    }

    private suspend fun fetchSources() {
        when (val result = userRepository.getUserSources(authRepository.getCurrentUserId(), false)) {
            is List<SourceModel> -> {
                _sources.value = result
            }

            null -> {
                _toastMessage.emit("Failed to fetch sources")
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            listOf(async { refreshUser() }, async { refreshSources() }).awaitAll()
            _isRefreshing.value = false
        }
    }

    private suspend fun refreshUser() {
        when (val result = userRepository.getUserById(authRepository.getCurrentUserId(), true)) {
            is UserModel -> {
                _user.value = result
            }

            null -> {
                _toastMessage.emit("Failed to fetch user data")
            }
        }
    }

    private suspend fun refreshSources() {
        when (val result = userRepository.getUserSources(authRepository.getCurrentUserId(), true)) {
            is List<SourceModel> -> {
                _sources.value = result
            }

            null -> {
                _toastMessage.emit("Failed to fetch sources")
            }
        }
    }
}