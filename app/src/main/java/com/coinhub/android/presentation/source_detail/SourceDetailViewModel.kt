package com.coinhub.android.presentation.source_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.SourceModel
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
class SourceDetailViewModel @Inject constructor() : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    private val _showBalanceErrorDialog = MutableStateFlow(false)
    val showBalanceErrorDialog: StateFlow<Boolean> = _showBalanceErrorDialog.asStateFlow()

    fun onDeleteClick(source: SourceModel) {
        if (source.balance == BigInteger.ZERO) {
            _showDeleteDialog.value = true
        } else {
            _showBalanceErrorDialog.value = true
        }
    }

    fun onDeleteConfirm() {
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

    fun dismissDeleteDialog() {
        _showDeleteDialog.value = false
    }

    fun dismissBalanceErrorDialog() {
        _showBalanceErrorDialog.value = false
    }
}
