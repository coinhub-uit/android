package com.coinhub.android.presentation.top_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TopUpProviderEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor() : ViewModel() {
    private val _sourceModels = MutableStateFlow(
        listOf(
            SourceModel("1", BigDecimal(1000000)), SourceModel("2", BigDecimal(500000)), SourceModel("3", BigDecimal(750000))
        )
    )
    val sourceModels = _sourceModels.asStateFlow()

    private val _vnpResponseCode = MutableStateFlow<String?>(null)
    val vnpResponseCode = _vnpResponseCode.asStateFlow()

    private val _isSourceBottomSheetVisible = MutableStateFlow(false)
    val isSourceBottomSheetVisible = _isSourceBottomSheetVisible.asStateFlow()

    private val _topUpProvider = MutableStateFlow<TopUpProviderEnum?>(null)
    val topUpProvider = _topUpProvider.asStateFlow()

    private val _amountText = MutableStateFlow("")
    val amountText = _amountText.asStateFlow()

    val isFormValid = combine(
        vnpResponseCode, topUpProvider, amountText
    ) { selectedSourceId, selectedProvider, amountText ->
        selectedSourceId != null && selectedProvider != null && amountText.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setShowSourceBottomSheet(show: Boolean) {
        _isSourceBottomSheetVisible.value = show
    }

    fun selectSource(sourceId: String) {
        _vnpResponseCode.value = sourceId
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

    // TODO: @NTGNguyen use use case to pass down props. Naming the fun again
    fun getCreateTopUpDto(): CreateTopUpDto {
        return CreateTopUpDto(
            provider = _topUpProvider.value!!,
            returnUrl = "coinhub://topUp/result",
            sourceDestinationId = _vnpResponseCode.value!!,
            ipAddress = "192.168.1.1",
            amount = _amountText.value.toLong()
        )
    }

    //fun getTopUpResult(): AppNavDestinations.TopUpResult {
    //return AppNavDestinations.TopUpResult(
    //   vnpResponseCode = vnpResponseCode.value!!

    //)
    //}
}
