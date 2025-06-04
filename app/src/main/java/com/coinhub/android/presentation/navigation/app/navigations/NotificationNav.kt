package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.notification.NotificationScreen

fun NavGraphBuilder.notificationNav(navController: NavHostController) {
    composable<AppNavDestinations.Notification> {
        NotificationScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}
