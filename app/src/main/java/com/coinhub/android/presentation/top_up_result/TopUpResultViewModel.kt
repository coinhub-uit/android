package com.coinhub.android.presentation.top_up_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.presentation.top_up_result.state.TopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TopUpResultViewModel @Inject constructor(
    private val getTopUpUseCase: GetTopUpUseCase,
) : ViewModel() {
    private val _topUpState = MutableStateFlow<TopUpState>(TopUpState.Loading)
    val topUpState = _topUpState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun checkTopUpStatus(topUpId: String?) {
        if (topUpId.isNullOrEmpty()) {
            _topUpState.value = TopUpState.Error("Top-up ID is null or empty")
            return
        }
        getTopUpUseCase(topUpId).onEach {
            when (it) {
                is GetTopUpUseCase.Result.Error -> {
                    _topUpState.value = TopUpState.Error(it.message)
                }

                GetTopUpUseCase.Result.Loading -> {
                    _topUpState.value = TopUpState.Loading
                }

                is GetTopUpUseCase.Result.Success -> {
                    _topUpState.value = TopUpState.Success(it.topUpModel)
                }
            }
        }.launchIn(viewModelScope)
    }
}

