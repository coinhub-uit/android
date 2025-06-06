package com.coinhub.android.domain.managers

import com.coinhub.android.data.dtos.request.CreateTicketRequestDto
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.use_cases.CreateTicketUseCase
import com.coinhub.android.domain.use_cases.GetUserTicketsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TicketManager @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserTicketUseCase: GetUserTicketsUseCase,
    private val createTicketUseCase: CreateTicketUseCase,
) {
    private val scope = CoroutineScope(ioDispatcher)

    private val _ticketModelsState = MutableStateFlow<TicketModelsState>(TicketModelsState.Loading)
    val ticketModelsState = _ticketModelsState.asStateFlow()

    fun getTickets() {
        if (_ticketModelsState.value is TicketModelsState.Success) {
            return
        }
        reloadTickets()
    }

    fun reloadTickets() {
        getUserTicketUseCase().onEach {
            when (it) {
                GetUserTicketsUseCase.Result.Loading -> {
                    _ticketModelsState.value = TicketModelsState.Loading
                }

                is GetUserTicketsUseCase.Result.Error -> {
                    _ticketModelsState.value = TicketModelsState.Error(it.message)
                }

                is GetUserTicketsUseCase.Result.Success -> {
                    _ticketModelsState.value = TicketModelsState.Success(it.ticketModels)
                }
            }

        }.launchIn(scope)
    }

    fun createTicket(createTicketRequestDto: CreateTicketRequestDto) {
        createTicketUseCase(createTicketRequestDto).onEach {
            when (it) {
                CreateTicketUseCase.Result.Loading -> {
                    _ticketModelsState.value = TicketModelsState.Loading
                }

                is CreateTicketUseCase.Result.Error -> {
                    _ticketModelsState.value = TicketModelsState.Error(it.message)
                }

                is CreateTicketUseCase.Result.Success -> {
                    if (_ticketModelsState.value is TicketModelsState.Success) {
                        val currentTickets = (_ticketModelsState.value as TicketModelsState.Success).ticketModels
                        _ticketModelsState.value = TicketModelsState.Success(currentTickets + it.ticketModel)
                    } else {
                        _ticketModelsState.value = TicketModelsState.Success(listOf(it.ticketModel))
                    }
                }
            }
        }.launchIn(scope)
    }

    sealed class TicketModelsState {
        data object Loading : TicketModelsState()
        data class Success(val ticketModels: List<TicketModel>) : TicketModelsState()
        data class Error(val message: String) : TicketModelsState()
    }
}