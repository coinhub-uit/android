package com.coinhub.android.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.CreateProfileUseCase
import com.coinhub.android.domain.use_cases.ValidateBirthDateUseCase
import com.coinhub.android.domain.use_cases.ValidateCitizenIdUseCase
import com.coinhub.android.domain.use_cases.ValidateFullNameUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import com.coinhub.android.utils.toMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import androidx.core.net.toUri

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val validateCitizenIdUseCase: ValidateCitizenIdUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
) : ViewModel() {
    var message = "" // WARN: Again check
        private set

    private val _avatarUri = MutableStateFlow(
        "https://via.placeholder.com/150".toUri())
    val avatarUri = _avatarUri.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    @OptIn(FlowPreview::class)
    val fullNameCheckState = _fullName.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validateFullNameUseCase(it)
        ProfileStates.FullNameCheckState(
            isValid = result is ValidateFullNameUseCase.Result.Success,
            errorMessage = if (result is ValidateFullNameUseCase.Result.Error) result.message else null
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileStates.FullNameCheckState(
        )
    )

    private val _birthDateInMillis = MutableStateFlow(LocalDate.now().toMillis())
    val birthDateInMillis = _birthDateInMillis.asStateFlow()

    @OptIn(FlowPreview::class)
    val birthDateCheckState = _birthDateInMillis.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validateBirthDateUseCase(it)
        ProfileStates.BirthDateCheckState(
            isValid = result is ValidateBirthDateUseCase.Result.Success,
            errorMessage = if (result is ValidateBirthDateUseCase.Result.Error) result.message else null
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileStates.BirthDateCheckState(
        )
    )

    private val _citizenId = MutableStateFlow("")
    val citizenId = _citizenId.asStateFlow()

    @OptIn(FlowPreview::class)
    val citizenIdCheckState = _citizenId.drop(1).debounce(DEBOUNCE_TYPING).map {
        val result = validateCitizenIdUseCase(it)
        ProfileStates.CitizenIdCheckState(
            isValid = result is ValidateCitizenIdUseCase.Result.Success,
            errorMessage = if (result is ValidateCitizenIdUseCase.Result.Error) result.message else null
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileStates.CitizenIdCheckState()
    )

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(
        fullNameCheckState, birthDateCheckState, citizenIdCheckState
    ) { fullNameCheckState, birthDateCheckState, citizenIdState ->
        fullNameCheckState.isValid && birthDateCheckState.isValid && citizenIdState.isValid
    }.drop(1).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    private val _createProfileStatus = MutableStateFlow(ProfileStates.ProfileStatus.Ready)
    val createProfileStatus = _createProfileStatus.asStateFlow()

    fun onAvatarUriChange(uri: Uri) {
        _avatarUri.value = uri
    }

    fun onFullNameChange(fullName: String) {
        _fullName.value = fullName
    }

    fun onBirthDateInMillisChange(birthDateInMillis: Long) {
        _birthDateInMillis.value = birthDateInMillis
    }

    fun onCitizenIdChange(citizenId: String) {
        _citizenId.value = citizenId
    }

    fun onAddressChange(address: String) {
        _address.value = address
    }

    fun onCreateProfile(onSuccess: () -> Unit, onError: () -> Unit) {
        if (!isFormValid.value) { // Actually in the UI will block this, so it's quite odd to check here
            return
        }

        viewModelScope.launch {
            createProfileUseCase(
                fullName = fullName.value,
                birthDateInMillis = birthDateInMillis.value,
                citizenId = _citizenId.value,
                address = _address.value.takeIf { it.isNotBlank() }).collect { result ->
                when (result) {
                    is CreateProfileUseCase.Result.Loading -> {
                        TODO()
                    }

                    is CreateProfileUseCase.Result.Success -> {
                        onSuccess()
                        TODO()
                    }

                    is CreateProfileUseCase.Result.Error -> {
                        onError()
                        TODO()
                    }
                }
            }
        }
    }
}
