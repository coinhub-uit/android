package com.coinhub.android.presentation.ticket_detail

import androidx.lifecycle.ViewModel
import com.coinhub.android.domain.managers.TicketManager
import javax.inject.Inject

class TicketDetailViewModel @Inject constructor(
    private val ticketManager: TicketManager,
) : ViewModel() {
    val ticketModelsState = ticketManager.ticketModelsState
}