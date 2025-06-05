package com.coinhub.android.presentation.create_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.use_cases.ValidateSourceIdUseCase
import com.coinhub.android.utils.DEBOUNCE_TYPING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateSourceViewModel @Inject constructor(
    private val validateSourceIdUseCase: ValidateSourceIdUseCase
) : ViewModel() {
    private val _sourceId = MutableStateFlow("")
    val sourceId = _sourceId.asStateFlow()

    @OptIn(FlowPreview::class)
    val isSourceIdValid = sourceId.drop(1).debounce(DEBOUNCE_TYPING).map { sourceId ->
        val result = validateSourceIdUseCase(sourceId = sourceId)
        CreateSourceStates.SourceCheckState(
            isValid = result is ValidateSourceIdUseCase.Result.Success,
            errorMessage = if (result is ValidateSourceIdUseCase.Result.Error) result.message else null
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreateSourceStates.SourceCheckState())

    val isFormValid = isSourceIdValid.map { it.isValid }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setSourceId(id: String) {
        _sourceId.value = id
    }

    // TODO: @NTGNguyen use case create source here
    fun createSource() {
        _isLoading.value = true
    }
}