package com.coinhub.android.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    // NOTE: This already filter which tickets are active

    private val _ticketsModelState = MutableStateFlow<TicketModelsState>(TicketModelsState.Loading)
    val ticketsModelState = _ticketsModelState.asStateFlow()

    fun getTickets(refresh: Boolean = false) {
        viewModelScope.launch {
            _ticketsModelState.value = TicketModelsState.Loading
            try {
                val userId = authRepository.getCurrentUserId()
                _ticketsModelState.value = TicketModelsState.Success(userRepository.getUserTickets(userId, refresh))
            } catch (e: Exception) {
                _ticketsModelState.value =
                    TicketModelsState.Error(e.message!!)
            }
        }
    }

    val totalPrincipal = _ticketsModelState.map { state ->
        when (state) {
            is TicketModelsState.Success -> state.ticketModels.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.principal ?: BigInteger.ZERO
            }

            else -> BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    val totalInterest = _ticketsModelState.map { state ->
        when (state) {
            is TicketModelsState.Success -> state.ticketModels.sumOf { ticket ->
                ticket.ticketHistories.firstOrNull()?.interest ?: BigInteger.ZERO
            }

            else -> BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    sealed class TicketModelsState {
        data object Loading : TicketModelsState()
        data class Success(val ticketModels: List<TicketModel>) : TicketModelsState()
        data class Error(val message: String) : TicketModelsState()
    }
}
