package com.coinhub.android.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import com.coinhub.android.utils.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor() : ViewModel() {
    // NOTE: This already filter which tickets are active
    private val _tickets = MutableStateFlow(
        listOf(
            TicketModel(
                id = 1,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            ), TicketModel(
                id = 2,
                openedAt = "02/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.PR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "02/01/2025".toLocalDate(),
                        maturedAt = "02/02/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 3, days = 60
                )
            ), TicketModel(
                id = 3,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.PIR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "02/02/2025".toLocalDate(),
                        maturedAt = "02/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    ), TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/02/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 3, days = 30
                )
            ),
            TicketModel(
                id = 4,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            ),
            TicketModel(
                id = 5,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            ),
            TicketModel(
                id = 6,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            ),
            TicketModel(
                id = 7,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.NR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            )
        )
    )

    val tickets: StateFlow<List<TicketModel>> = _tickets

    val totalPrincipal = _tickets.map { tickets ->
        tickets.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.principal ?: BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    val totalInterest = _tickets.map { tickets ->
        tickets.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.interest ?: BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)
}
