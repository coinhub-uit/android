package com.coinhub.android.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.domain.use_cases.CheckUserRegisterProfileUseCase
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkUserRegisterProfileUseCase: CheckUserRegisterProfileUseCase,
    private val getUserSourcesUseCase: GetUserSourcesUseCase,
) : ViewModel() {
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

    private val _sourceModels = MutableStateFlow<List<SourceModel>>(emptyList())
    val sourceModels = _sourceModels.asStateFlow()

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }

    fun checkUserRegisterProfile(onNavigateToRegisterProfile: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            when (val result = checkUserRegisterProfileUseCase()) {
                is CheckUserRegisterProfileUseCase.Result.Success -> {
                    when (result.user) {
                        null -> onNavigateToRegisterProfile()
                        else -> {
                            _userModel.value = result.user
                            getUserSources()
                        }
                    }
                }

                is CheckUserRegisterProfileUseCase.Result.Error -> {
                    onError()
                }
            }
        }
    }

    private fun getUserSources() {
        viewModelScope.launch {
            when (val result = getUserSourcesUseCase()) {
                is GetUserSourcesUseCase.Result.Success -> {
                    _sourceModels.value = result.sources
                }

                is GetUserSourcesUseCase.Result.Error -> {
                    throw Exception(result.message)
                }
            }
        }
    }
}