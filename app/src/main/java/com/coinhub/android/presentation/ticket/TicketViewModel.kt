package com.coinhub.android.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val preferenceDataStore: PreferenceDataStore,
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _tickets = MutableStateFlow<List<TicketModel>>(emptyList())
    val tickets = _tickets.asStateFlow()

    private val _totalPrincipal = MutableStateFlow(BigInteger("0"))
    val totalPrincipal = _totalPrincipal.asStateFlow()

    private val _totalInterest = MutableStateFlow(BigInteger("0"))
    val totalInterest = _totalInterest.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            preferenceDataStore.getTotalPrincipal().collect { value ->
                if (value != null) {
                    _totalPrincipal.value = value
                }
            }
        }
        viewModelScope.launch(ioDispatcher) {
            preferenceDataStore.getTotalInterest().collect { value ->
                if (value != null) {
                    _totalInterest.value = value
                }
            }
        }
    }

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
