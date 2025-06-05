package com.coinhub.android.presentation.top_up_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TopUpResultViewModel @Inject constructor(
    private val getTopUpUseCase: GetTopUpUseCase,
) : ViewModel() {
    private val _topUp = MutableStateFlow<TopUpModel?>(null)
    val topUp: StateFlow<TopUpModel?> = _topUp.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String?>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    fun checkTopUpStatus(topUpId: String?) {
        if (topUpId.isNullOrEmpty()) {
            _toastMessage.tryEmit("Top-up ID is null or empty")
            return
        }
        getTopUpUseCase(topUpId).takeWhile { it !is GetTopUpUseCase.Result.Success }.onEach {
            when (it) {
                is GetTopUpUseCase.Result.Error -> {
                    _toastMessage.emit(it.message)
                }

                is GetTopUpUseCase.Result.Success -> {
                    _topUp.value = it.topUp
                }

                GetTopUpUseCase.Result.Loading -> {
                }
            }
        }.map {
            when (it) {
                is GetTopUpUseCase.Result.Error, is GetTopUpUseCase.Result.Success -> _isLoading.update {
                    false
                }

                GetTopUpUseCase.Result.Loading -> _isLoading.update {
                    true
                }
            }
        }.launchIn(viewModelScope)
    }
}

