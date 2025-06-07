package com.coinhub.android.presentation.create_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.dtos.request.CreateSourceRequestDto
import com.coinhub.android.domain.use_cases.CreateSourceUseCase
import com.coinhub.android.domain.use_cases.ValidateSourceIdUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSourceViewModel @Inject constructor(
    private val validateSourceIdUseCase: ValidateSourceIdUseCase,
    private val createSourceUseCase: CreateSourceUseCase,
) : ViewModel() {
    private val _sourceId = MutableStateFlow("")
    val sourceId = _sourceId.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val sourceCheckState = sourceId.drop(1).debounce(DEBOUNCE_TYPING).mapLatest { sourceId ->
        val result = validateSourceIdUseCase(sourceId = sourceId)
        CreateSourceStates.SourceCheckState(
            isValid = result is ValidateSourceIdUseCase.Result.Success,
            errorMessage = if (result is ValidateSourceIdUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreateSourceStates.SourceCheckState())

    val isFormValid =
        sourceCheckState.drop(1).map { it.isValid }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    fun setSourceId(id: String) {
        _sourceId.value = id
    }

    fun createSource(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            createSourceUseCase(CreateSourceRequestDto(id = _sourceId.value)).let {
                when (it) {
                    is CreateSourceUseCase.Result.Error -> {
                        _isProcessing.value = false
                    }

                    is CreateSourceUseCase.Result.Success -> {
                        onSuccess()
                        _isProcessing.value = false
                    }
                }
            }
        }
    }
}