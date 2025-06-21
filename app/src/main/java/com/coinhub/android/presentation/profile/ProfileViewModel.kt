package com.coinhub.android.presentation.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CreateProfileUseCase
import com.coinhub.android.domain.use_cases.UpdateProfileUseCase
import com.coinhub.android.domain.use_cases.UploadAvatarUseCase
import com.coinhub.android.domain.use_cases.ValidateBirthDateUseCase
import com.coinhub.android.domain.use_cases.ValidateCitizenIdUseCase
import com.coinhub.android.domain.use_cases.ValidateFullNameUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import com.coinhub.android.utils.toMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val validateCitizenIdUseCase: ValidateCitizenIdUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val preferenceDataStore: PreferenceDataStore,
    private val supabaseService: SupabaseService,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : ViewModel() {
    private val _avatarUri = MutableStateFlow<Uri?>(
        null
    )
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

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

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

    fun loadProfile() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            val user = userRepository.getUserById(userId, false)
            user?.let {
                _fullName.value = it.fullName
                _citizenId.value = it.citizenId
                _birthDateInMillis.value = it.birthDate.toMillis()
                _avatarUri.value = it.avatar?.toUri()
                _address.value = it.address ?: ""
            }
        }
    }

    fun onCreateProfile() {
        viewModelScope.launch {
            val createProfileResult = createProfileUseCase(
                fullName = fullName.value,
                birthDateInMillis = birthDateInMillis.value,
                citizenId = _citizenId.value,
                address = _address.value.takeIf { it.isNotBlank() },
                avatar = _avatarUri.value.toString().takeIf {
                    it.isNotBlank()
                },
            )

            uploadAvatar()

            when (createProfileResult) {
                is CreateProfileUseCase.Result.Success -> {
                    if (preferenceDataStore.getLockPin().isNullOrEmpty()) {
                        supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SET_LOCKED_PIN)
                    } else {
                        supabaseService.setIsUserSignedIn(SupabaseService.UserAppState.SIGNED_IN)
                    }
                }

                is CreateProfileUseCase.Result.Error -> {
                }
            }
        }
    }

    fun onUpdateProfile(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            val updateProfileResult = updateProfileUseCase(
                userId = authRepository.getCurrentUserId(),
                fullName = _fullName.value.takeIf { it.isNotBlank() },
                birthDateInMillis = _birthDateInMillis.value,
                citizenId = _citizenId.value.takeIf { it.isNotBlank() },
                avatar = if (_avatarUri.value != null) _avatarUri.value.toString() else null,
                address = _address.value.takeIf { it.isNotBlank() })

            if (_avatarUri.value != null) {
                uploadAvatar()
            }

            when (updateProfileResult) {
                is UpdateProfileUseCase.Result.Error -> {
                    _toastMessage.emit(updateProfileResult.message)
                }

                is UpdateProfileUseCase.Result.Success -> {
                    _toastMessage.emit("Profile updated successfully")
                    onSuccess()
                }
            }
        }
    }

    private suspend fun uploadAvatar() {
        _avatarUri.value?.let { uri ->
            uploadAvatarUseCase(authRepository.getCurrentUserId(), uri)
        }
    }
}
