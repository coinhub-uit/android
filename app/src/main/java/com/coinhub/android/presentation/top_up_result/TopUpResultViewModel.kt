package com.coinhub.android.presentation.top_up_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.TopUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpResultViewModel @Inject constructor(
//    private val topUpRepository: TopUpRepository,
) : ViewModel() {
    private val _topUpModel = MutableStateFlow<TopUpModel?>(null)
    val topUpModel = _topUpModel.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun checkTopUpStatus(sourceId: String) {
        viewModelScope.launch {
            try {
//                val topUpModel = topUpRepository.getTopUp(
//                    sourceId = sourceId
//                )
//                _topUpModel.value = topUpModel
            } catch (e: Exception) {
                _message.value = "Error checking status: ${e.message}"
                _message.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
