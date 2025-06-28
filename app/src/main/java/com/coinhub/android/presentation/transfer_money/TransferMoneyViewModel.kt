package com.coinhub.android.presentation.transfer_money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.TransferMoneyRequestDto
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.SourceRepository
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.use_cases.TransferMoneyUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferMoneyViewModel @Inject constructor(
    private val sourceRepository: SourceRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepositoryImpl,
    private val transferMoneyUseCase: TransferMoneyUseCase,
) : ViewModel() {
    private val _sources = MutableStateFlow<List<SourceModel>>(
        emptyList()
    )
    val sources = _sources.asStateFlow()

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId = _selectedSourceId.asStateFlow()

    private val _receiptSourceId = MutableStateFlow("")
    val receiptSourceId = _receiptSourceId.asStateFlow()

    private val _amountText = MutableStateFlow("")
    val amountText = _amountText.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val receiptUser = _receiptSourceId.debounce(DEBOUNCE_TYPING).mapLatest {
        sourceRepository.getSourceUser(_receiptSourceId.value)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val isFormValid = combine(
        _selectedSourceId,
        receiptUser,
        _amountText
    ) { sourceId, receiptUser, amountText ->
        val isSourceIdValid = !sourceId.isNullOrEmpty()
        val isAmountValid = try {
            val amount = amountText.toLongOrNull() ?: 0
            amount > 0 && amount <= _sources.value.find { it.id == sourceId }!!.balance.toLong()
        } catch (e: NumberFormatException) {
            false
        }
        isSourceIdValid && receiptUser != null && isAmountValid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    fun selectSourceId(sourceId: String) {
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

    init {
        viewModelScope.launch {
            _isLoading.value = true
            fetchSources()
            _isLoading.value = false
        }
    }

    fun transferMoney(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            transferMoneyUseCase(
                TransferMoneyRequestDto(
                    money = _amountText.value.toBigInteger(),
                    fromSourceId = _selectedSourceId.value!!,
                    toSourceId = _receiptSourceId.value,
                )
            ).let {
                when (it) {
                    is TransferMoneyUseCase.Result.Error -> {
                        _isProcessing.value = false
                        _toastMessage.emit(it.message)
                    }

                    TransferMoneyUseCase.Result.Success -> {
                        _isProcessing.value = false
                        val userId = authRepository.getCurrentUserId()
                        _sources.value = userRepository.getUserSources(userId, true)
                        onSuccess()
                    }
                }
            }
            _isProcessing.value = false
        }
    }

    private suspend fun fetchSources() {
        val userId = authRepository.getCurrentUserId()
        _sources.value = userRepository.getUserSources(userId, false)
    }
}
