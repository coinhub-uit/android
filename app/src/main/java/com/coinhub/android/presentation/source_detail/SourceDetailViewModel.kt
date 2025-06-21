package com.coinhub.android.presentation.source_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.use_cases.DeleteSourceUseCase
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class SourceDetailViewModel @Inject constructor(
    private val deleteSourceUseCase: DeleteSourceUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showCloseDialog = MutableStateFlow(false)
    val showCloseDialog: StateFlow<Boolean> = _showCloseDialog.asStateFlow()

    private val _showBalanceErrorDialog = MutableStateFlow(false)
    val showBalanceErrorDialog: StateFlow<Boolean> = _showBalanceErrorDialog.asStateFlow()

    fun onCloseClick(source: SourceModel) {
        if (source.balance == BigInteger.ZERO) {
            _showCloseDialog.value = true
        } else {
            _showBalanceErrorDialog.value = true
        }
    }

    fun onCloseConfirm() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000) // Simulate network delay
            _isLoading.value = false
        }
    }

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }

    fun dismissCloseDialog() {
        _showCloseDialog.value = false
    }

    fun dismissBalanceErrorDialog() {
        _showBalanceErrorDialog.value = false
    }

    fun deleteSource(source: SourceModel) {
        viewModelScope.launch {
            _isLoading.value = true
            when (deleteSourceUseCase(source.id)) {
                is DeleteSourceUseCase.Result.Error -> {
                    _isLoading.value = false
                }

                is DeleteSourceUseCase.Result.Success -> {
                    _isLoading.value = false
                }
            }
        }
    }
}
