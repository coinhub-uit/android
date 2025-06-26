package com.coinhub.android.presentation.ai_chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.AiChatModel
import com.coinhub.android.domain.repositories.AiChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiChatViewModel @Inject constructor(
    private val aiChatRepository: AiChatRepository,
) : ViewModel() {
    private lateinit var rawMessages: List<AiChatModel>

    val messages = mutableStateListOf<AiChatModel>()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            messages.add(
                AiChatModel(
                    content = "Hello! How can I assist you today?",
                    role = AiChatModel.Role.ASSISTANT,
                )
            )
            rawMessages = aiChatRepository.getContext()
            _isLoading.value = false
        }
    }

    fun onMessageChange(newText: String) {
        _message.update {
            newText
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            _isProcessing.value = true
            val newMessage = AiChatModel(content = _message.value, role = AiChatModel.Role.USER)
            messages.add(newMessage)
            rawMessages += newMessage
            _message.value = ""
            val aiResponse = aiChatRepository.send(rawMessages)
            messages.add(aiResponse)
            rawMessages += aiResponse
            _isProcessing.value = false
        }
    }
}
