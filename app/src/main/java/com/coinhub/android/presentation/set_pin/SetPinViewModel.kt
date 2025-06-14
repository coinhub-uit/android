package com.coinhub.android.presentation.set_pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.di.IoDispatcher
import com.coinhub.android.domain.managers.LockHashingManager
import com.coinhub.android.domain.use_cases.ValidatePinUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPinViewModel @Inject constructor(
    private val supabaseService: SupabaseService,
    private val validatePinUseCase: ValidatePinUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val lockHashingManager: LockHashingManager,
) : ViewModel() {
    private val _pin = MutableStateFlow("")
    val pin = _pin.asStateFlow()

    @OptIn(FlowPreview::class)
    val pinCheckState = _pin.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validatePinUseCase(it)
        SetPinStates.PinCheckState(
            isValid = result is ValidatePinUseCase.Result.Success,
            errorMessage = if (result is ValidatePinUseCase.Result.Error) result.message else null
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = SetPinStates.PinCheckState()
    )

    val isFormValid = pinCheckState.map { it.isValid }
        .stateIn(viewModelScope, started = WhileSubscribed(5000), initialValue = false)

    fun onPinChange(pin: String) {
        if (pin.length > 4) return
        _pin.value = pin
    }

    fun onChangePin() {
        viewModelScope.launch(ioDispatcher) {
            lockHashingManager.save(_pin.value)
        }
    }

    fun onCreatePin() {
        viewModelScope.launch(ioDispatcher) {
            lockHashingManager.save(_pin.value)
            supabaseService.setIsUserSignedIn(
                SupabaseService.UserAppState
                    .SIGNED_IN
            )
        }
    }
}