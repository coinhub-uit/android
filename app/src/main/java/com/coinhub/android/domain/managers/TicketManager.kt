package com.coinhub.android.domain.managers

import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.use_cases.GetUserTicketsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketManager @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserTicketUseCase: GetUserTicketsUseCase,
) {
    private val scope = CoroutineScope(ioDispatcher)

    private val _ticketModels = MutableStateFlow<TicketModelsState>(TicketModelsState.Loading)
    val ticketModels = _ticketModels.asStateFlow()

    fun getTickets() {
        if (_ticketModels.value is TicketModelsState.Success) {
            return
        }
        scope.launch {
            _ticketModels.value = TicketModelsState.Loading
            when (val result = getUserTicketUseCase()) {
                is GetUserTicketsUseCase.Result.Error -> {
                    _ticketModels.value = TicketModelsState.Error(result.message)
                }

                is GetUserTicketsUseCase.Result.Success -> {
                    _ticketModels.value = TicketModelsState.Success(result.ticketModels)
                }
            }
        }
    }

    fun reloadTickets() {
        scope.launch {
            _ticketModels.value = TicketModelsState.Loading
            when (val result = getUserTicketUseCase()) {
                is GetUserTicketsUseCase.Result.Error -> {
                    _ticketModels.value = TicketModelsState.Error(result.message)
                }

                is GetUserTicketsUseCase.Result.Success -> {
                    _ticketModels.value = TicketModelsState.Success(result.ticketModels)
                }
            }
        }
    }

    sealed class TicketModelsState {
        data object Loading : TicketModelsState()
        data class Success(val ticketModels: List<TicketModel>) : TicketModelsState()
        data class Error(val message: String) : TicketModelsState()
    }
}