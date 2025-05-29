package com.coinhub.android.presentation.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.ai_chat.AiChatScreen
import com.coinhub.android.presentation.edit_profile.EditProfileScreen
import com.coinhub.android.presentation.main.MainScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.nested.createSourceNestedGraph
import com.coinhub.android.presentation.navigation.app.nested.createTicketNestedGraph
import com.coinhub.android.presentation.navigation.app.nested.topUpNestedGraph
import com.coinhub.android.presentation.notification.NotificationScreen
import com.coinhub.android.presentation.settings.SettingsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavDestinations.Main) {
        composable<AppNavDestinations.Main> {
            MainScreen(navController = navController)
        }

        composable<AppNavDestinations.Notification>{
            NotificationScreen()
        }

        createSourceNestedGraph(navController = navController)

        topUpNestedGraph(navController = navController)

        createTicketNestedGraph()

        composable<AppNavDestinations.AiChat> {
            AiChatScreen()
        }

        composable<AppNavDestinations.EditProfile> {
            EditProfileScreen()
        }

        composable<AppNavDestinations.Settings> {
            SettingsScreen()
        }
    }
}