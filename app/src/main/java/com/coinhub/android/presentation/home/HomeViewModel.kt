package com.coinhub.android.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    @OptIn(ExperimentalUuidApi::class)
    private val _userModel = MutableStateFlow(
        UserModel(
            id = Uuid.random(),
            birthDate = LocalDate.parse("2000-01-01"),
            citizenId = "1234567890123",
            createdAt = LocalDate.parse("2023-01-01"),
            deletedAt = null,
            avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
            fullName = "NTGNguyen",
            address = null
        )
    )
    val userModel = _userModel.asStateFlow()

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }
}