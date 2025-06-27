package com.coinhub.android.presentation.top_up_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpResultViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val getTopUpUseCase: GetTopUpUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _topUp = MutableStateFlow<TopUpModel?>(null)
    val topUp: StateFlow<TopUpModel?> = _topUp.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun checkTopUpStatus(topUpId: String?) {
        if (topUpId.isNullOrEmpty()) {
            _toastMessage.tryEmit("Top-up ID is null or empty")
            return
        }
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            when (val result = getTopUpUseCase(topUpId)) {
                is GetTopUpUseCase.Result.Error -> {
                    _toastMessage.tryEmit(result.message)
                }

                is GetTopUpUseCase.Result.Success -> {
                    _topUp.update { result.topUp }
                    userRepositoryImpl.getUserSources(authRepository.getCurrentUserId(), true)
                }
            }
            _isLoading.value = false
        }
    }
}