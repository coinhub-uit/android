package com.coinhub.android.presentation.top_up_result.state

import com.coinhub.android.data.models.TopUpModel

sealed class TopUpState {
    data object Loading : TopUpState()
    data class Success(val topUpModel: TopUpModel) : TopUpState()
    data class Error(val message: String) : TopUpState()
}