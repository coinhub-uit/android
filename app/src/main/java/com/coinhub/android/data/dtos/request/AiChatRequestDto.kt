package com.coinhub.android.data.dtos.request

import com.coinhub.android.domain.models.AiChatModel

data class AiChatRequestDto(
    val messages: List<AiChatModel>
)
