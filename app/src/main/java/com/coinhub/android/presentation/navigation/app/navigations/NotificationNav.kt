package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.notification.NotificationScreen

fun NavGraphBuilder.notificationNav() {
    composable<AppNavDestinations.Notification> {
        NotificationScreen()
    }
}
