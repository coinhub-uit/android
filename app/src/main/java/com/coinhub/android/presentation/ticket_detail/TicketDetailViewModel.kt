package com.coinhub.android.presentation.ticket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.TicketRepository
import com.coinhub.android.domain.use_cases.WithdrawTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val withdrawTicketUseCase: WithdrawTicketUseCase,
    private val planRepository: PlanRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {
    private val _ticket = MutableStateFlow<TicketModel?>(null)
    val ticket = _ticket.asStateFlow()

    private val _withdrawPlan = MutableStateFlow<AvailablePlanModel?>(null)
    val withdrawPlan = _withdrawPlan.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isWithdrawing = MutableStateFlow(false)
    val isWithdrawing = _isWithdrawing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun fetch(ticketId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            listOf(
                async {
                    _ticket.update {
                        ticketRepository.getTicketById(ticketId, false)
                    }
                },
                async {
                    _withdrawPlan.update {
                        planRepository.getAvailablePlans()?.find { it.days == -1 }
                    }
                },
            ).awaitAll()
            _isLoading.value = false
        }
    }

    fun withdrawTicket(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (_ticket.value == null) {
                return@launch
            }
            _isWithdrawing.value = true
            when (val result = withdrawTicketUseCase(_ticket.value!!.id)) {
                is WithdrawTicketUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is WithdrawTicketUseCase.Result.Success -> {
                    val userId = authRepository.getCurrentUserId()
                    listOf(
                        async {
                            userRepository.getUserTickets(userId, true)
                        },
                        async {
                            userRepository.getUserSources(userId, true)
                        },
                    ).awaitAll()
                    onSuccess()
                }
            }
            _isWithdrawing.value = false
        }
    }
}
