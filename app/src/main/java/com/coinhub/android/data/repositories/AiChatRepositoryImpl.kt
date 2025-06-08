package com.coinhub.android.data.repositories

import com.coinhub.android.common.toAiChatModel
import com.coinhub.android.data.api_services.AiChatApiService
import com.coinhub.android.data.dtos.request.AiChatRequestDto
import com.coinhub.android.domain.models.AiChatModel
import com.coinhub.android.domain.repositories.AiChatRepository
import javax.inject.Inject

class AiChatRepositoryImpl @Inject constructor(
    private val aiChatApiService: AiChatApiService,
) : AiChatRepository {
    override suspend fun getSession(): List<AiChatModel> {
        val aiChatResponseDtos = aiChatApiService.getSession()
        val aiChatModels = aiChatResponseDtos.map { it.toAiChatModel() }
        return aiChatModels
    }

    override suspend fun send(message: String): AiChatModel {
        val aiChatResponseDto = aiChatApiService.send(
            AiChatRequestDto(
                message = message
            )
        )
        val aiChatModel = aiChatResponseDto.toAiChatModel()
        return aiChatModel
    }

    override suspend fun deleteSession() {
        aiChatApiService.deleteSession()
    }
}
