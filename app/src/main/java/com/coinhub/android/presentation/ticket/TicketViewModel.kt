package com.coinhub.android.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.managers.TicketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketManager: TicketManager,
) : ViewModel() {
    // NOTE: This already filter which tickets are active

    val ticketsState: StateFlow<TicketManager.TicketModelsState> = ticketManager.ticketModelsState

    val getTickets = ticketManager::getTickets
    val reloadTickets = ticketManager::reloadTickets

    val totalPrincipal = ticketsState.map { state ->
        when (state) {
            is TicketManager.TicketModelsState.Success -> state.ticketModels.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.principal ?: BigInteger.ZERO
            }

            else -> BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    val totalInterest = ticketsState.map { state ->
        when (state) {
            is TicketManager.TicketModelsState.Success -> state.ticketModels.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.interest ?: BigInteger.ZERO
            }

            else -> BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)
}
