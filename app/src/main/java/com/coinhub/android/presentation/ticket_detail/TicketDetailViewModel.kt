package com.coinhub.android.presentation.ticket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.TicketRepository
import com.coinhub.android.domain.use_cases.WithdrawTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val withdrawTicketUseCase: WithdrawTicketUseCase,
    private val planRepository: PlanRepository,
) : ViewModel() {
    private val _ticket = MutableStateFlow<TicketModel?>(null)
    val ticket = _ticket.asStateFlow()

    private val _withdrawPlan = MutableStateFlow<AvailablePlanModel?>(null)
    val withdrawPlan = _withdrawPlan.asStateFlow()

    val isLoading = combine(
        _ticket, _withdrawPlan
    ) { ticket, plan -> ticket == null || plan == null }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), true
    )

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun getTicketAndWithdrawPlan(ticketId: Int) {
        viewModelScope.launch {
            listOf(async {
                _ticket.update {
                    ticketRepository.getTicketById(ticketId, false)
                }
            }, async {
                _withdrawPlan.update {
                    planRepository.getAvailablePlans()?.find { it.days == -1 }
                }
            }).awaitAll()
        }
    }

    fun withdrawTicket(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = _ticket.value?.let {
                withdrawTicketUseCase(it.id)
            }) {
                is WithdrawTicketUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is WithdrawTicketUseCase.Result.Success -> {
                    onSuccess()
                }

                null -> _toastMessage.emit("Ticket not found")
            }
        }
    }
}