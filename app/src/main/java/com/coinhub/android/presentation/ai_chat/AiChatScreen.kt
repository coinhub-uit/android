package com.coinhub.android.presentation.ai_chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.AiChatModel
import com.coinhub.android.presentation.ai_chat.components.AiChatMessageItem
import com.coinhub.android.presentation.ai_chat.components.AiChatTopBar
import com.coinhub.android.presentation.ai_chat.components.ChatInputField
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@Composable
fun AiChatScreen(
    onBack: () -> Unit,
    viewModel: AiChatViewModel = hiltViewModel(),
) {
    val message = viewModel.message.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isProcessing = viewModel.isProcessing.collectAsStateWithLifecycle().value

    AiChatScreen(
        message = message,
        onMessageChange = viewModel::onMessageChange,
        onSendMessage = viewModel::sendMessage,
        messages = viewModel.messages,
        isLoading = isLoading,
        isProcessing = isProcessing,
        onBack = onBack,
    )
}

@Composable
private fun AiChatScreen(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    messages: List<AiChatModel>,
    isLoading: Boolean,
    isProcessing: Boolean,
    onBack: () -> Unit,
) {
    val listState = rememberLazyListState()

    // Scroll to the bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            AiChatTopBar(
                onBack = onBack,
            )
        },
    ) { innerPadding ->
        if (isLoading || isProcessing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = listState,
            ) {
                items(messages) { message ->
                    AiChatMessageItem(message = message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Message input
            ChatInputField(
                value = message,
                onValueChange = onMessageChange,
                onSend = onSendMessage,
                isLoading = isLoading,
                isProcessing = isProcessing,
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun AiChatScreenPreview() {
    CoinhubTheme {
        val messages = listOf(
            AiChatModel(
                content = "Hello! How can I assist you today?",
                role = AiChatModel.Role.ASSISTANT,
            ), AiChatModel(
                content = "I have a question about cryptocurrency",
                role = AiChatModel.Role.USER,
            ), AiChatModel(
                content = "Sure, I'd be happy to help with your cryptocurrency questions. What would you like to know?",
                role = AiChatModel.Role.ASSISTANT,
            ), AiChatModel(
                content = "How do I check the current Bitcoin price?",
                role = AiChatModel.Role.USER,
            ), AiChatModel(
                content = "To check the current Bitcoin price, you can use the price chart function in the app or go to the markets tab.",
                role = AiChatModel.Role.ASSISTANT,
            )
        )

        AiChatScreen(
            messages = messages,
            onSendMessage = {},
            onBack = {},
            message = "Cool",
            onMessageChange = { _ -> },
            isLoading = false,
            isProcessing = false,
        )
    }
}
