package com.coinhub.android.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.domain.managers.UserManager
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserSourcesUseCase: GetUserSourcesUseCase,
    private val userManager: UserManager,
) : ViewModel() {
    val userModel = userManager.user

    private val _sourceModels = MutableStateFlow<List<SourceModel>>(emptyList())
    val sourceModels = _sourceModels.asStateFlow()

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }

    fun refresh() {
        viewModelScope.launch {
            userManager.reloadUser()
            getUserSources()
        }
    }

    fun getUserSources() {
        viewModelScope.launch {
            when (val result = getUserSourcesUseCase()) {
                is GetUserSourcesUseCase.Result.Success -> {
                    _sourceModels.value = result.sources
                }

                is GetUserSourcesUseCase.Result.Error -> {
                    throw Exception(result.message)
                }
            }
        }
    }
}