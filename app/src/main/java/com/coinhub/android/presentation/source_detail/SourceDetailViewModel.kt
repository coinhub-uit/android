package com.coinhub.android.presentation.source_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CloseSourceUseCase
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceDetailViewModel @Inject constructor(
    private val closeSourceUseCase: CloseSourceUseCase,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }

    fun closeSource(source: SourceModel, onClosed: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            when (val result = closeSourceUseCase(source.id)) {
                is CloseSourceUseCase.Result.Error -> {
                    _toastMessage.emit(result.message)
                }

                is CloseSourceUseCase.Result.Success -> {
                    val userId = authRepository.getCurrentUserId()
                    userRepository.getUserSources(userId, true)
                    onClosed()
                }
            }
            _isProcessing.value = false
        }
    }
}
