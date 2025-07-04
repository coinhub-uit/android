package com.coinhub.android.presentation.top_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.BuildConfig
import com.coinhub.android.data.dtos.request.CreateTopUpRequestDto
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.models.CreateTopUpModel
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val createTopUpUseCase: CreateTopUpUseCase,
    private val userRepository: UserRepositoryImpl,
    private val authRepository: AuthRepository,
) : ViewModel() {
    init {
        viewModelScope.launch {
            getUserSources()
        }
    }

    private val _sources = MutableStateFlow<List<SourceModel>>(emptyList())
    val sources = _sources.asStateFlow()

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId = _selectedSourceId.asStateFlow()

    private val _createTopUp = MutableSharedFlow<CreateTopUpModel>()
    val createTopUp = _createTopUp.asSharedFlow()

    private val _topUpProvider = MutableStateFlow<TopUpModel.ProviderEnum?>(null)
    val topUpProvider = _topUpProvider.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    val isFormValid = combine(
        _selectedSourceId, topUpProvider, amount
    ) { selectedSourceId, selectedProvider, amountText ->
        selectedSourceId != null && selectedProvider != null && amountText.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun selectSource(sourceId: String) {
        _selectedSourceId.value = sourceId
    }

    fun selectProvider(provider: TopUpModel.ProviderEnum) {
        _topUpProvider.value = provider
    }

    fun updateAmount(amount: String) {
        _amount.value = amount
    }

    fun setPresetAmount(amount: String) {
        _amount.value = amount.replace(".", "")
    }

    fun createTopUp() {
        viewModelScope.launch {
            when (val result = createTopUpUseCase(
                CreateTopUpRequestDto(
                    amount = _amount.value.toBigInteger(),
                    provider = _topUpProvider.value.toString(),
                    sourceDestinationId = _selectedSourceId.value!!,
                    ipAddress = "192.0.0.1", //TODO:How to get IP address in Android?
                    returnUrl = BuildConfig.vnpayReturnUrl
                )
            )) {
                is CreateTopUpUseCase.Result.Success -> {
                    _createTopUp.emit(result.createTopUpModel)
                }

                is CreateTopUpUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }
            }
        }
    }

    private fun getUserSources(refresh: Boolean = false) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            _sources.value = userRepository.getUserSources(userId, refresh)
        }
    }
}
