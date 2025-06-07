package com.coinhub.android.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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
    private val _tickets = MutableStateFlow<List<TicketModel>>(emptyList())
    val tickets = _tickets.asStateFlow()

    val totalPrincipal = tickets.map {
        it.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.principal ?: BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    val totalInterest = tickets.map {
        it.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.interest ?: BigInteger.ZERO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BigInteger.ZERO)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>(0)
    val toastMessage = _toastMessage.asSharedFlow()

    fun fetch() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            val tickets = userRepository.getUserTickets(userId, false)
            if (tickets == null) {
                _toastMessage.emit("No tickets found")
            }
            _tickets.value = tickets
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // TODO: refresh here, and no catch
            } catch (e: Exception) {
                _toastMessage.emit("Error refreshing tickets: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
