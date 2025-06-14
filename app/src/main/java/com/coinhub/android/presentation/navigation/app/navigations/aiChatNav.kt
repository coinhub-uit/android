package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.ai_chat.AiChatScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.animations.slideInFromRight
import com.coinhub.android.presentation.navigation.animations.slideOutFromLeft

fun NavGraphBuilder.aiChatNav(navController: NavHostController) {
    composable<AppNavDestinations.AiChat>(
        enterTransition = slideInFromRight,
        exitTransition = slideOutFromLeft
    ) {
        AiChatScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}
