package com.coinhub.android.presentation.create_ticket

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.SettingRepository
import com.coinhub.android.domain.use_cases.CreateTicketUseCase
import com.coinhub.android.domain.use_cases.ValidateAmountCreateTicketUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val validateAmountCreateTicketUseCase: ValidateAmountCreateTicketUseCase,
    private val createTicketUseCase: CreateTicketUseCase,
    private val planRepository: PlanRepository,
    private val userRepository: UserRepositoryImpl,
    private val authRepository: AuthRepository,
    private val settingRepository: SettingRepository,
) : ViewModel() {
    private val _minimumAmount = MutableStateFlow(0L)
    val minimumAmount = _minimumAmount.asStateFlow()

    private val _availablePlans = MutableStateFlow<List<AvailablePlanModel>>(emptyList())
    val availablePlans: StateFlow<List<AvailablePlanModel>> = _availablePlans.asStateFlow()

    private val _sources = MutableStateFlow<List<SourceModel>>(
        emptyList()
    )
    val sources: StateFlow<List<SourceModel>> = _sources.asStateFlow()

    private val _amountText = MutableStateFlow("")
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    @OptIn(FlowPreview::class)
    val amountError = _amountText.drop(1).debounce(DEBOUNCE_TYPING).map { amountText ->
        val result = validateAmountCreateTicketUseCase(
            amountText, _minimumAmount.value, _sources.value.find { source -> source.id == _selectedSourceId.value })
        return@map if (result is ValidateAmountCreateTicketUseCase.Result.Error) result.message else null
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = null
    )

    private val _selectedAvailablePlan = MutableStateFlow<AvailablePlanModel?>(null)
    val selectedAvailablePlan: StateFlow<AvailablePlanModel?> = _selectedAvailablePlan.asStateFlow()

    private val _selectedMethod = MutableStateFlow<MethodEnum?>(null)
    val selectedMethod: StateFlow<MethodEnum?> = _selectedMethod.asStateFlow()

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId: StateFlow<String?> = _selectedSourceId.asStateFlow()

    val isFormValid = combine(
        amountError, _selectedAvailablePlan, _selectedMethod, _selectedSourceId
    ) { amountError, plan, method, sourceId ->
        plan != null && method != null && sourceId != null && amountError == null
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = false
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun updateAmount(amount: String) {
        if (!amount.isDigitsOnly()) {
            return
        }
        _amountText.value = amount
    }

    fun selectPlan(availablePlan: AvailablePlanModel) {
        _selectedAvailablePlan.value = availablePlan
    }

    fun selectMethod(method: MethodEnum) {
        _selectedMethod.value = method
    }

    fun selectSourceId(sourceId: String) {
        _selectedSourceId.value = sourceId
    }

    fun createTicket(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            val result = createTicketUseCase(
                // TODO: @NTGNguyen no dto here
                CreateTicketRequestDto(
                    amount = _amountText.value.toLong(),
                    planHistoryId = _selectedAvailablePlan.value!!.planHistoryId,
                    method = _selectedMethod.value!!.toString(),
                    sourceId = _selectedSourceId.value!!
                )
            )
            when (result) {
                is CreateTicketUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is CreateTicketUseCase.Result.Success -> {
                    val userId = authRepository.getCurrentUserId()
                    userRepository.getUserTickets(userId, true)
                    onSuccess()
                }
            }
            _isProcessing.value = false
        }
    }

    init {
        viewModelScope.launch {
            _isLoading.value = true
            listOf(
                async { getAvailablePlans() },
                async { getUserSources() },
                async { getGetMinimumAmount() }
            ).awaitAll()
            _isLoading.value = false
        }
    }

    private suspend fun getAvailablePlans() {
        val result = planRepository.getAvailablePlans()
        if (result == null) {
            _toastMessage.emit("Failed to load available plans")
            return
        }
        _availablePlans.value = result.filter { it.days != -1 }
    }

    private suspend fun getUserSources() {
        val userId = authRepository.getCurrentUserId()
        _sources.value = userRepository.getUserSources(userId, false)
    }

    private fun getGetMinimumAmount() {
        viewModelScope.launch {
            _minimumAmount.value = settingRepository.getSettings()?.minAmountOpenTicket ?: 0L
        }
    }
}
