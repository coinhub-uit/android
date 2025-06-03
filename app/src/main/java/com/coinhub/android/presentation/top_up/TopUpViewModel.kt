package com.coinhub.android.presentation.top_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.BuildConfig
import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
import com.coinhub.android.presentation.top_up.state.TopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val createTopUpUseCase: CreateTopUpUseCase,
    private val getTopUpUseCase: GetTopUpUseCase,
    private val getUserSourcesUseCase: GetUserSourcesUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            getUserSources()
        }
    }

    private val _sourceModels = MutableStateFlow<List<SourceModel>>(emptyList())
    val sourceModels = _sourceModels.asStateFlow()

    private val _sourceId = MutableStateFlow<String?>(null)
    val sourceId = _sourceId.asStateFlow()

    private val _vnpResponseCode = MutableStateFlow<String?>(null)
    val vnpResponseCode = _vnpResponseCode.asStateFlow()

    private val _createTopUpModel = MutableSharedFlow<CreateTopUpModel>(replay = 0)
    val createTopUpModel = _createTopUpModel.asSharedFlow()

    private val _isSourceBottomSheetVisible = MutableStateFlow(false)
    val isSourceBottomSheetVisible = _isSourceBottomSheetVisible.asStateFlow()

    private val _topUpProvider = MutableStateFlow<TopUpProviderEnum?>(null)
    val topUpProvider = _topUpProvider.asStateFlow()

    private val _amountText = MutableStateFlow("")
    val amountText = _amountText.asStateFlow()

    val isFormValid = combine(
        _sourceId, topUpProvider, amountText
    ) { selectedSourceId, selectedProvider, amountText ->
        selectedSourceId != null && selectedProvider != null && amountText.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setShowSourceBottomSheet(show: Boolean) {
        _isSourceBottomSheetVisible.value = show
    }

    fun selectSource(sourceId: String) {
        _sourceId.value = sourceId
        _isSourceBottomSheetVisible.value = false
    }

    fun selectProvider(provider: TopUpProviderEnum) {
        _topUpProvider.value = provider
    }

    fun updateAmount(amount: String) {
        if (amount.isEmpty() || amount.all { it.isDigit() }) {
            _amountText.value = amount
        }
    }

    fun setPresetAmount(amount: String) {
        _amountText.value = amount.replace(".", "")
    }

    fun createTopUp() {
        viewModelScope.launch {
            when (val result = createTopUpUseCase(
                CreateTopUpDto(
                    amount = _amountText.value.toBigInteger(),
                    provider = _topUpProvider.value.toString(),
                    sourceDestinationId = _sourceId.value!!,
                    ipAddress = "192.0.0.1", //TODO:How to get IP address in Android?
                    returnUrl = BuildConfig.vnpayReturnUrl
                )
            )
            ) {
                is CreateTopUpUseCase.Result.Success -> {
                    result.createTopUpModel.let { _createTopUpModel.emit(it) }
                }

                is CreateTopUpUseCase.Result.Error -> {
                    throw Exception(result.message)
                }
            }
        }
    }

    private fun getUserSources() {
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

    //--------------------------------------------------------//
    private val _topUpState = MutableStateFlow<TopUpState>(TopUpState.Loading)
    val topUpState = _topUpState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun checkTopUpStatus() {
        getTopUpUseCase(_sourceId.value!!).onEach {
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
