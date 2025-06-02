package com.coinhub.android.presentation.create_ticket

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateTicketDto
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.SourceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor() : ViewModel() {
    // TODO: If have time, implement repository to fetch real data
    private val _minimumAmount = 1000000L
    val minimumAmount get() = _minimumAmount

    // State flows for UI state
    private val _amountText = MutableStateFlow("")
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    private val _selectedPlan = MutableStateFlow<AvailablePlanModel?>(null)
    val selectedPlan: StateFlow<AvailablePlanModel?> = _selectedPlan.asStateFlow()

    private val _selectedMethod = MutableStateFlow<MethodEnum?>(null)
    val selectedMethod: StateFlow<MethodEnum?> = _selectedMethod.asStateFlow()

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId: StateFlow<String?> = _selectedSourceId.asStateFlow()

    private val _isPlanBottomSheetVisible = MutableStateFlow(false)
    val isPlanBottomSheetVisible: StateFlow<Boolean> = _isPlanBottomSheetVisible.asStateFlow()

    private val _isMethodBottomSheetVisible = MutableStateFlow(false)
    val isMethodBottomSheetVisible: StateFlow<Boolean> = _isMethodBottomSheetVisible.asStateFlow()

    private val _isSourceBottomSheetVisible = MutableStateFlow(false)
    val isSourceBottomSheetVisible: StateFlow<Boolean> = _isSourceBottomSheetVisible.asStateFlow()

    // Mock data for demo/preview
    private val _availablePlans = MutableStateFlow(
        listOf(
            AvailablePlanModel(1, 5.5f, 1, 30),
            AvailablePlanModel(2, 7.2f, 2, 60),
            AvailablePlanModel(3, 8.5f, 3, 90)
        )
    )
    val availablePlans: StateFlow<List<AvailablePlanModel>> = _availablePlans.asStateFlow()

    private val _sourceModels = MutableStateFlow(
        listOf(
            SourceModel("1", BigInteger("5000000")),
            SourceModel("2", BigInteger("3000000")),
            SourceModel("3", BigInteger("7500000"))
        )
    )
    val sourceModels: StateFlow<List<SourceModel>> = _sourceModels.asStateFlow()

    val isFormValid = combine(
        _amountText,
        _selectedPlan,
        _selectedMethod,
        _selectedSourceId
    ) { amount, plan, method, sourceId ->
        (amount.toLongOrNull() ?: 0L) >= _minimumAmount && plan != null && method != null && sourceId != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    // Functions to update state
    fun updateAmount(amount: String) {
        if (!amount.isDigitsOnly()){
            return
        }
        _amountText.value = amount
    }

    fun selectPlan(plan: AvailablePlanModel) {
        _selectedPlan.value = plan
    }

    fun selectMethod(method: MethodEnum) {
        _selectedMethod.value = method
    }

    fun selectSource(sourceId: String) {
        _selectedSourceId.value = sourceId
    }

    fun setShowPlanBottomSheet(show: Boolean) {
        _isPlanBottomSheetVisible.value = show
    }

    fun setShowMethodBottomSheet(show: Boolean) {
        _isMethodBottomSheetVisible.value = show
    }

    fun setShowSourceBottomSheet(show: Boolean) {
        _isSourceBottomSheetVisible.value = show
    }

    fun createTicket() {
        val ticketDto = CreateTicketDto(
            sourceId = _selectedSourceId.value ?: return,
            methodEnum = _selectedMethod.value ?: return,
            planHistoryId = _selectedPlan.value?.planHistoryId?.toString() ?: return,
            amount = _amountText.value.toLongOrNull() ?: return
        )

        // Here you would call a repository to create the ticket
        // For now, we just validate the form
    }
}
