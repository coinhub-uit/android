package com.coinhub.android.presentation.vault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor(): ViewModel() {
    
    private val _tickets = MutableStateFlow<List<TicketModel>>(
        listOf(
            TicketModel(
                id = "1",
                openedAt = "2023-01-01",
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistory = listOf(
                    TicketHistoryModel(
                        issuedAt = "2023-01-01",
                        maturedAt = null,
                        principal = "1000000",
                        interest = "50000"
                    )
                )
            ),
            TicketModel(
                id = "2",
                openedAt = "2023-02-01",
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.PR,
                ticketHistory = listOf(
                    TicketHistoryModel(
                        issuedAt = "2023-02-01",
                        maturedAt = null,
                        principal = "2000000",
                        interest = "100000"
                    )
                )
            ),
            TicketModel(
                id = "3",
                openedAt = "2023-03-01",
                closedAt = "2023-03-15",
                status = TicketStatus.EARLY_WITH_DRAWN,
                method = MethodEnum.PIR,
                ticketHistory = listOf(
                    TicketHistoryModel(
                        issuedAt = "2023-03-01",
                        maturedAt = "2023-03-15",
                        principal = "3000000",
                        interest = "75000"
                    )
                )
            )
        )
    )
    
    val tickets: StateFlow<List<TicketModel>> = _tickets
    
    val totalPrincipal = _tickets
        .map { tickets ->
            tickets.sumOf { ticket ->
                ticket.ticketHistory.firstOrNull()?.principal?.toLongOrNull() ?: 0L
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
        
    val totalInterest = _tickets
        .map { tickets ->
            tickets.sumOf { ticket ->
                ticket.ticketHistory.firstOrNull()?.interest?.toLongOrNull() ?: 0L
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
        
    val availablePlans = MutableStateFlow(3)
}
