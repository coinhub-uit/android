package com.coinhub.android.presentation.ai_chat

import android.util.Log
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
    private val _messages = mutableStateListOf<AiChatModel>()
    val messages: List<AiChatModel> get() = _messages

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val session = aiChatRepository.getSession()
            if (session.isEmpty()) {
                addInitialMessage()
            } else {
                _messages.addAll(
                    session.drop(1) // Drop the system prompt lol
                )
            }
            _isLoading.value = false
        }
    }

    fun onMessageChange(newText: String) {
        _message.update {
            newText
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            aiChatRepository.deleteSession()
        }
        _messages.clear()
        _message.value = ""
        addInitialMessage()
    }

    fun sendMessage() {
        viewModelScope.launch {
            _isProcessing.value = true
            _messages.add(
                AiChatModel(message = _message.value, role = AiChatModel.Role.USER)
            )
            _message.value = ""
            val aiChat = aiChatRepository.send(_message.value)
            _messages.add(aiChat)
            _isProcessing.value = false
        }
    }

    private fun addInitialMessage() {
        _messages.add(
            AiChatModel(
                message = "Hello! How can I assist you today?", role = AiChatModel.Role.ASSISTANT
            )
        )
    }
}
