package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.AiChatModel

interface AiChatRepository {
    suspend fun getContext(): List<AiChatModel>

    suspend fun send(messages: List<AiChatModel>): AiChatModel
}
