package com.coinhub.android.presentation.transfer_money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class TransferMoneyViewModel @Inject constructor() : ViewModel() {
    // TODO: @NTGNguyen - Replace with real data source
    private val _sources = MutableStateFlow(
        listOf(
            SourceModel("1", BigInteger("5000000")),
            SourceModel("2", BigInteger("3000000")),
            SourceModel("3", BigInteger("7500000"))
        )
    )
    val sources: StateFlow<List<SourceModel>> = _sources

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId: StateFlow<String?> = _selectedSourceId

    // Receipt source ID input
    private val _receiptSourceId = MutableStateFlow("")
    val receiptSourceId: StateFlow<String> = _receiptSourceId

    // Amount input
    private val _amountText = MutableStateFlow("")
    val amountText: StateFlow<String> = _amountText

    @OptIn(FlowPreview::class, ExperimentalUuidApi::class)
    val receiptUser = _receiptSourceId.drop(1).debounce(DEBOUNCE_TYPING).map {
        // @NTGNguyen - Fetch user by source id
        UserModel(
            id = Uuid.random(),
            fullName = "Nguyen Van A",
            citizenId = "123456789",
            birthDate = LocalDate.now(),
            avatar = "https://example.com/avatar.png",
            address = "123 Street, City",
            createdAt = ZonedDateTime.now(),
            deletedAt = null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Form validation
    val isFormValid = combine(
        _selectedSourceId,
        receiptUser,
        _amountText
    ) { sourceId, receiptUser, amountText ->
        val isSourceIdValid = !sourceId.isNullOrEmpty()
        val isAmountValid = try {
            val amount = amountText.toLongOrNull() ?: 0
            amount > 0
        } catch (e: NumberFormatException) {
            false
        }

        isSourceIdValid && receiptUser != null && isAmountValid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun selectSource(sourceId: String) {
        _selectedSourceId.value = sourceId
    }

    fun updateReceiptSourceId(id: String) {
        _receiptSourceId.value = id
    }

    fun updateAmount(amount: String) {
        // Only allow digits
        if (amount.isEmpty() || amount.all { it.isDigit() }) {
            _amountText.value = amount
        }
    }

    fun transferMoney(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            // Simulate API call
            delay(1500)

            // Reset form
            _selectedSourceId.value = null
            _receiptSourceId.value = ""
            _amountText.value = ""

            _isLoading.value = false
            onSuccess()
        }
    }
}
