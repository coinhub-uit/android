package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.ai_chat.AiChatScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.aiChatNav() {
    composable<AppNavDestinations.AiChat> {
        AiChatScreen()
    }
}
