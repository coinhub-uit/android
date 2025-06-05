package com.coinhub.android.presentation.ai_chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.coinhub.android.data.models.AiChatModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AiChatViewModel @Inject constructor() : ViewModel() {
    private val _messages = mutableStateListOf<AiChatModel>()
    val messages: List<AiChatModel> get() = _messages

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // NOTE: Fetch the chat data remember to slice the first element because it is the system prompt :))
    init {
        // Add welcome message
        _messages.add(
            AiChatModel(
                message = "Hello! How can I assist you today?", role = AiChatModel.Role.ASSISTANT
            )
        )
    }

    fun onMessageChange(newText: String) {
        _message.update {
            newText
        }
    }

    fun deleteSession() {
        _messages.clear()
        _message.update {
            ""
        }
        _messages.add(
            AiChatModel(
                message = "Hello! How can I assist you today?", role = AiChatModel.Role.ASSISTANT
            )
        )
    }

    fun sendMessage() {
        val userMessage = AiChatModel(
            message = _message.value, role = AiChatModel.Role.USER
        )
        _messages.add(userMessage)
    }
}
