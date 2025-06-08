package com.coinhub.android.domain.repositories

import com.coinhub.android.domain.models.AiChatModel

interface AiChatRepository {
    suspend fun getSession(): List<AiChatModel>

    suspend fun send(message: String): AiChatModel

    suspend fun deleteSession()
}
