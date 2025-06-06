package com.coinhub.android.presentation.ticket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.TicketRepository
import com.coinhub.android.domain.use_cases.WithdrawTicketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketDetailViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val withdrawTicketUseCase: WithdrawTicketUseCase,
    private val planRepository: PlanRepository,
) : ViewModel() {

    private val _ticketModelState = MutableStateFlow<TicketModelState>(TicketModelState.Loading)
    val ticketModelState = _ticketModelState.asStateFlow()

    private val _plan = MutableStateFlow<AvailablePlanModel?>(null)
    val plan = _plan.asStateFlow()

    fun getTicket(ticketId: Int, refresh: Boolean = false) {
        viewModelScope.launch {
            _ticketModelState.value = TicketModelState.Loading
            try {
                val ticket = ticketRepository.getTicketById(ticketId, refresh)
                _ticketModelState.value = TicketModelState.Success(ticket)
                _plan.value = planRepository.getAvailablePlans().find { it.planId == ticket.plan.id }
            } catch (e: Exception) {
                _ticketModelState.value =
                    TicketModelState.Error(e.message ?: "An error occurred while fetching the ticket")
            }
        }
    }

    fun withdrawTicket(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            if (_ticketModelState.value is TicketModelState.Success) {
                when (val result =
                    withdrawTicketUseCase((_ticketModelState.value as TicketModelState.Success).ticketModel.id)) {
                    is WithdrawTicketUseCase.Result.Error -> {
                        onError(result.message)
                    }

                    is WithdrawTicketUseCase.Result.Success -> {
                        onSuccess(result.message)
                    }
                }
            }
        }
    }

    sealed class TicketModelState {
        data object Loading : TicketModelState()
        data class Success(val ticketModel: TicketModel) : TicketModelState()
        data class Error(val message: String) : TicketModelState()
    }
}