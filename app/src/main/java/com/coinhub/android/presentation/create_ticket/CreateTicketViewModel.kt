package com.coinhub.android.presentation.create_ticket

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CreateTicketUseCase
import com.coinhub.android.domain.use_cases.ValidateAmountCreateTicketUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val validateAmountCreateTicketUseCase: ValidateAmountCreateTicketUseCase,
    private val createTicketUseCase: CreateTicketUseCase,
    private val planRepository: PlanRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    // TODO: If have time, implement repository to fetch real data
    private val _minimumAmount = MutableStateFlow(1_000_000L)
    val minimumAmount = _minimumAmount.asStateFlow()

    // Mock data for demo/preview
    private val _availablePlans = MutableStateFlow<List<AvailablePlanModel>?>(
        null
    )
    val availablePlans: StateFlow<List<AvailablePlanModel>?> = _availablePlans.asStateFlow()

    private val _sources = MutableStateFlow<List<SourceModel>?>(
        null
    )
    val sources: StateFlow<List<SourceModel>?> = _sources.asStateFlow()

    // State flows for UI state
    private val _amountText = MutableStateFlow("")
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    @OptIn(FlowPreview::class)
    val amountError = _amountText.debounce(DEBOUNCE_TYPING).map { amountText ->
        val result =
            validateAmountCreateTicketUseCase(
                amountText,
                _minimumAmount.value,
                _sources.value?.find { source -> source.id == _selectedSourceId.value })
        return@map if (result is ValidateAmountCreateTicketUseCase.Result.Error) result.message else null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _selectedAvailablePlan = MutableStateFlow<AvailablePlanModel?>(null)
    val selectedAvailablePlan: StateFlow<AvailablePlanModel?> = _selectedAvailablePlan.asStateFlow()

    private val _selectedMethod = MutableStateFlow<MethodEnum?>(null)
    val selectedMethod: StateFlow<MethodEnum?> = _selectedMethod.asStateFlow()

    private val _selectedSourceId = MutableStateFlow<String?>(null)
    val selectedSourceId: StateFlow<String?> = _selectedSourceId.asStateFlow()

    val isFormValid = combine(
        amountError,
        _selectedAvailablePlan,
        _selectedMethod,
        _selectedSourceId
    ) { amountError, plan, method, sourceId ->
        plan != null && method != null && sourceId != null && amountError == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    // TODO: @NGTNguyen add state or sth
    val isLoading = MutableStateFlow(false)

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

    private val _createTicketState = MutableStateFlow<CreateTicketState>(CreateTicketState.Loading)
    val createTicketState: StateFlow<CreateTicketState> = _createTicketState.asStateFlow()

    fun createTicket() {
        createTicketUseCase(
            CreateTicketRequestDto(
                amount = _amountText.value.toLong(),
                planHistoryId = _selectedAvailablePlan.value!!.planHistoryId,
                methodEnum = _selectedMethod.value!!,
                sourceId = _selectedSourceId.value!!
            )
        ).onEach {
            when (it) {
                is CreateTicketUseCase.Result.Error -> {
                    _createTicketState.value = CreateTicketState.Error(it.message)
                }

                CreateTicketUseCase.Result.Loading -> {
                    _createTicketState.value = CreateTicketState.Loading
                }

                is CreateTicketUseCase.Result.Success -> {
                    _createTicketState.value = CreateTicketState.Success
                    _amountText.value = ""
                    _selectedAvailablePlan.value = null
                    _selectedMethod.value = null
                    _selectedSourceId.value = null
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAvailablePlans() {
        viewModelScope.launch {
            when (val result = planRepository.getAvailablePlans()) {
                is List<AvailablePlanModel> -> {
                    _availablePlans.value = result
                }

                null -> {
                    //TODO: handle
                }
            }
        }
    }

    fun getUserSources() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            when (val result = userRepository.getUserSources(userId, false)) {
                is List<SourceModel> -> {
                    _sources.value = result
                }

                null -> {
                    //TODO: handle
                }
            }
        }
    }

    sealed class CreateTicketState {
        data object Loading : CreateTicketState()
        data object Success : CreateTicketState()
        data class Error(val message: String) : CreateTicketState()
    }
}
