package com.coinhub.android.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.CreateProfileUseCase
import com.coinhub.android.domain.use_cases.ValidateBirthDateUseCase
import com.coinhub.android.domain.use_cases.ValidateCitizenIdUseCase
import com.coinhub.android.domain.use_cases.ValidateFullNameUseCase
import com.coinhub.android.presentation.states.profile.BirthDateState
import com.coinhub.android.utils.toDateString
import com.kevinnitro.coinhub.presentation.profile.state.AddressState
import com.kevinnitro.coinhub.presentation.profile.state.CitizenIdState
import com.coinhub.android.presentation.states.profile.CreateProfileState
import com.coinhub.android.presentation.states.profile.FullNameState
import com.coinhub.android.utils.toMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val validateCitizenIdUseCase: ValidateCitizenIdUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
) : ViewModel() {

    private val _fullNameState = MutableStateFlow(FullNameState())
    val fullNameState: StateFlow<FullNameState> = _fullNameState.asStateFlow()

    private val _birthDateState = MutableStateFlow(BirthDateState(LocalDate.now().toMillis()))
    val birthDateState: StateFlow<BirthDateState> = _birthDateState.asStateFlow()

    private val _citizenIdState = MutableStateFlow(CitizenIdState())
    val citizenIdState: StateFlow<CitizenIdState> = _citizenIdState.asStateFlow()

    private val _addressState = MutableStateFlow(AddressState())
    val addressState: StateFlow<AddressState> = _addressState.asStateFlow()

    val isFormValid: StateFlow<Boolean> = combine(
        fullNameState,
        birthDateState,
        citizenIdState
    ) { fullName, birthDate, citizenId ->
        fullName.isValid && birthDate.isValid && citizenId.isValid
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    private val _createProfileState = MutableStateFlow(CreateProfileState())
    val createProfileState: StateFlow<CreateProfileState> = _createProfileState.asStateFlow()

    fun onFullNameChanged(fullName: String) {
        viewModelScope.launch {
            val result = validateFullNameUseCase(fullName)
            _fullNameState.update {
                it.copy(
                    fullName = fullName,
                    isValid = result is ValidateFullNameUseCase.Result.Success,
                    errorMessage = if (result is ValidateFullNameUseCase.Result.Error) result.message else null,
                )
            }
        }
    }

    fun onBirthDateChanged(birthDateInMillis: Long) {
        viewModelScope.launch {
            val validationResult = validateBirthDateUseCase(birthDateInMillis)
            _birthDateState.update {
                it.copy(
                    birthDateInMillis = birthDateInMillis,
                    birthDateInString = birthDateInMillis.toDateString(),
                    isValid = validationResult is ValidateBirthDateUseCase.Result.Success,
                    errorMessage = if (validationResult is ValidateBirthDateUseCase.Result.Error) validationResult.message else null
                )
            }
        }
    }

    fun onCitizenIdChanged(citizenId: String) {
        viewModelScope.launch {
            val validationResult = validateCitizenIdUseCase(citizenId)
            _citizenIdState.update {
                it.copy(
                    citizenId = citizenId,
                    isValid = validationResult is ValidateCitizenIdUseCase.Result.Success,
                    errorMessage = if (validationResult is ValidateCitizenIdUseCase.Result.Error) validationResult.message else null,
                )
            }
        }
    }

    fun onAddressChanged(address: String) {
        _addressState.update {
            it.copy(value = address)
        }
    }

    fun createProfile() {
        if (!isFormValid.value) {
            return
        }

        viewModelScope.launch {
            createProfileUseCase(
                fullName = _fullNameState.value.fullName,
                birthDateInMillis = _birthDateState.value.birthDateInMillis,
                citizenId = _citizenIdState.value.citizenId,
                address = _addressState.value.value.takeIf { it.isNotBlank() }
            ).collect { result ->
                when (result) {
                    is CreateProfileUseCase.Result.Loading -> {
                        _createProfileState.update { it.copy(isInProgress = true, error = null) }
                    }

                    is CreateProfileUseCase.Result.Success -> {
                        _createProfileState.update { it.copy(isInProgress = false, error = null) }
                    }

                    is CreateProfileUseCase.Result.Error -> {
                        _createProfileState.update {
                            it.copy(
                                isInProgress = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}
