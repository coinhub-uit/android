package com.coinhub.android.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.domain.managers.UserManager
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
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
    private val getUserSourcesUseCase: GetUserSourcesUseCase,
    private val userManager: UserManager,
) : ViewModel() {
    val user = userManager.user

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
        // TODO: Clean?
        if (userManager.user.value != null) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            listOf(async { fetchSources() }, async { fetchUser() }).awaitAll()
            _isLoading.value = false
        }
    }

    // FIXME: No catch
    private suspend fun fetchUser() {
        try {
            userManager.reloadUser()
        } catch (e: Exception) {
            _toastMessage.emit("Error fetching user: ${e.message}")
        }
    }

    private suspend fun fetchSources() {
        when (val result = getUserSourcesUseCase(false)) {
            is GetUserSourcesUseCase.Result.Success -> {
                _sources.value = result.sources
            }

            is GetUserSourcesUseCase.Result.Error -> {
                _toastMessage.emit(result.message)
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
        userManager.reloadUser()
    }

    private suspend fun refreshSources() {
        when (val result = getUserSourcesUseCase(true)) {
            is GetUserSourcesUseCase.Result.Success -> {
                _sources.value = result.sources
            }

            is GetUserSourcesUseCase.Result.Error -> {
                _toastMessage.emit(result.message)
            }
        }
    }
}