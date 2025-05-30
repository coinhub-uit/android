package com.coinhub.android.presentation.top_up.components

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
    // Source selection state
    private val _selectedSourceId = mutableStateOf<String?>(null)
    val selectedSourceId: State<String?> = _selectedSourceId

    private val _isSourceBottomSheetVisible = mutableStateOf(false)
    val isSourceBottomSheetVisible: State<Boolean> = _isSourceBottomSheetVisible

    // Mock data for sources
    private val _availableSources = mutableStateOf(
        listOf(
            SourceModel("1", 1000000), SourceModel("2", 500000), SourceModel("3", 750000)
        )
    )
    val availableSources: State<List<SourceModel>> = _availableSources

    // Provider selection state
    private val _selectedProvider = mutableStateOf<TopUpProviderEnum?>(null)
    val selectedProvider: State<TopUpProviderEnum?> = _selectedProvider

    // Amount input state
    private val _amountText = mutableStateOf("")
    val amountText: State<String> = _amountText

    // FAB visibility state, derived from other states
    val isFormValid: State<Boolean> = derivedStateOf {
        selectedSourceId.value != null && selectedProvider.value != null && amountText.value.isNotEmpty()
    }

    // Functions for state updates
    fun showSourceBottomSheet() {
        _isSourceBottomSheetVisible.value = true
    }

    fun hideSourceBottomSheet() {
        _isSourceBottomSheetVisible.value = false
    }

    fun selectSource(sourceId: String) {
        _selectedSourceId.value = sourceId
        hideSourceBottomSheet()
    }

    fun selectProvider(provider: TopUpProviderEnum) {
        _selectedProvider.value = provider
    }

    fun updateAmount(amount: String) {
        // Only accept numeric values
        if (amount.isEmpty() || amount.all { it.isDigit() }) {
            _amountText.value = amount
        }
    }

    fun setPresetAmount(amount: String) {
        _amountText.value = amount.replace(".", "")
    }
}
