package com.coinhub.android.presentation.vault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor() : ViewModel() {

    private val _tickets = MutableStateFlow<List<TicketModel>>(
        listOf(
            TicketModel(
                id = 1,
                openedAt = LocalDate.parse("2023-01-01"),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = LocalDate.parse("2023-01-01"),
                        maturedAt = null,
                        principal = 1000000,
                        interest = 50000
                    )
                ), plan = PlanModel(
                    id = 3,
                    days = 30
                )
            ),
            TicketModel(
                id = 1,
                openedAt = LocalDate.parse("2023-01-01"),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = LocalDate.parse("2023-01-01"),
                        maturedAt = null,
                        principal = 1000000,
                        interest = 50000
                    )
                ), plan = PlanModel(
                    id = 3,
                    days = 30
                )
            ),
            TicketModel(
                id = 1,
                openedAt = LocalDate.parse("2023-01-01"),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = LocalDate.parse("2023-01-01"),
                        maturedAt = null,
                        principal = 1000000,
                        interest = 50000
                    )
                ), plan = PlanModel(
                    id = 3,
                    days = 30
                )
            )
        )
    )

    val tickets: StateFlow<List<TicketModel>> = _tickets

    val totalPrincipal = _tickets
        .map { tickets ->
            tickets.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.principal!!.toLong()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val totalInterest = _tickets
        .map { tickets ->
            tickets.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.interest!!.toLong()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val availablePlans = MutableStateFlow(3)
}
