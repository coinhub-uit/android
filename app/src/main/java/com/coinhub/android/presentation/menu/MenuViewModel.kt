package com.coinhub.android.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val supabaseService: SupabaseService,
) : ViewModel() {
    @OptIn(ExperimentalUuidApi::class)
    private val _userModel = MutableStateFlow(
        UserModel(
            id = Uuid.random(),
            birthDate = LocalDate.parse("2000-01-01"),
            citizenId = "1234567890123",
            createdAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
            fullName = "NTGNguyen",
            address = null
        )
    )
    val userModel = _userModel.asStateFlow()

    fun onSignOut() {
        viewModelScope.launch {
            supabaseService.signOut()
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            // TODO: @NTGNguyen Logic to delete the user account
        }
    }
}
