package com.coinhub.android.presentation.ticket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import com.coinhub.android.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

class TicketDetailViewModel @Inject constructor(
) : ViewModel() {
    private val _ticket = MutableStateFlow<TicketModel?>(null)
    val ticket = _ticket.asStateFlow()

    private val _withdrawPlan = MutableStateFlow<AvailablePlanModel?>(null)
    val withdrawPlan = _withdrawPlan.asStateFlow()

    init {
        _withdrawPlan.value = AvailablePlanModel(
            planHistoryId = 1, rate = 0.04f, planId = 2, days = 90
        )
    }

    suspend fun getTicket(ticketId: Int) {
        // Fetch from repo
        _ticket.value = TicketModel(
            id = 1,
            openedAt = "01/01/2025".toLocalDate(),
            closedAt = null,
            status = TicketStatus.ACTIVE,
            method = MethodEnum.PR,
            ticketHistories = listOf(
                TicketHistoryModel(
                    issuedAt = "01/09/2025".toLocalDate(),
                    maturedAt = "01/11/2025".toLocalDate(),
                    principal = BigInteger("1000000"),
                    interest = BigInteger("40000")
                ), TicketHistoryModel(
                    issuedAt = "01/07/2025".toLocalDate(),
                    maturedAt = "01/09/2025".toLocalDate(),
                    principal = BigInteger("1000000"),
                    interest = BigInteger("90000")
                ), TicketHistoryModel(
                    issuedAt = "01/05/2025".toLocalDate(),
                    maturedAt = "01/07/2025".toLocalDate(),
                    principal = BigInteger("1000000"),
                    interest = BigInteger("23000")
                ), TicketHistoryModel(
                    issuedAt = "01/03/2025".toLocalDate(),
                    maturedAt = "01/05/2025".toLocalDate(),
                    principal = BigInteger("1000000"),
                    interest = BigInteger("54000")
                ), TicketHistoryModel(
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
    }

    fun withdraw(ticketId: Int) {
        viewModelScope.launch {}
    }
}
