package com.coinhub.android.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

@HiltViewModel
class CreateProfileViewModel @Inject constructor() : ViewModel() {
    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _citizenId = MutableStateFlow("")
    val citizenId: StateFlow<String> = _citizenId.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    fun setFullName(fullName: String) {
        _fullName.value = fullName
    }

    fun setBirthDate(birthDate: String) {
        _birthDate.value = birthDate
    }

    fun setCitizenId(newValue: String) {
        _citizenId.value = newValue
    }

    fun setAddress(newValue: String) {
        _address.value = newValue
    }
}

