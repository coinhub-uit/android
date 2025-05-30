package com.coinhub.android.presentation.top_up

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TopUpProviderEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor() : ViewModel() {
    private val _selectedSourceId = mutableStateOf<String?>(null)
    val selectedSourceId: State<String?> = _selectedSourceId

    private val _isSourceBottomSheetVisible = mutableStateOf(false)
    val isSourceBottomSheetVisible: State<Boolean> = _isSourceBottomSheetVisible

    private val _sourceModels = mutableStateOf(
        listOf(
            SourceModel("1", 1000000), SourceModel("2", 500000), SourceModel("3", 750000)
        )
    )
    val sourceModels: State<List<SourceModel>> = _sourceModels

    private val _selectedProvider = mutableStateOf<TopUpProviderEnum?>(null)
    val selectedProvider: State<TopUpProviderEnum?> = _selectedProvider

    private val _amountText = mutableStateOf("")
    val amountText: State<String> = _amountText

    val isFormValid: State<Boolean> = derivedStateOf {
        selectedSourceId.value != null && selectedProvider.value != null && amountText.value.isNotEmpty()
    }

    fun setShowSourceBottomSheet(show: Boolean) {
        _isSourceBottomSheetVisible.value = show
    }

    fun selectSource(sourceId: String) {
        _selectedSourceId.value = sourceId
        _isSourceBottomSheetVisible.value = false
    }

    fun selectProvider(provider: TopUpProviderEnum) {
        _selectedProvider.value = provider
    }

    fun updateAmount(amount: String) {
        if (amount.isEmpty() || amount.all { it.isDigit() }) {
            _amountText.value = amount
        }
    }

    fun setPresetAmount(amount: String) {
        _amountText.value = amount.replace(".", "")
    }
}
