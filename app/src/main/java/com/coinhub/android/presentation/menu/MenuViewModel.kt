package com.coinhub.android.presentation.menu

import androidx.lifecycle.ViewModel
import com.coinhub.android.data.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    @OptIn(ExperimentalUuidApi::class)
    private val _userModel = MutableStateFlow(
        UserModel(
            id = Uuid.random().toString(),
            birthDate = LocalDate.now().toString(),
            citizenId = "1234567890123",
            createdAt = Date().toString(),
            deletedAt = null,
            avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
            fullName = "NTGNguyen",
            address = null
        )
    )
    val userModel = _userModel.asStateFlow()
}
