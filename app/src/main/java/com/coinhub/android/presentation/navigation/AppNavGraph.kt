package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.ai_chat.AiChatScreen
import com.coinhub.android.presentation.main.MainScreen
import com.coinhub.android.presentation.notification.NotificationScreen
import io.github.jan.supabase.SupabaseClient

@Composable
fun AppNavGraph(
    isSignIn: Boolean,
    supabaseClient: SupabaseClient,
) {
    val navController = rememberNavController()

    // TODO: Split these like,...
    // https://github.com/android/compose-samples/blob/73b3a51e06a6520efb5b4931e71b771d257bf1dd/Jetsnack/app/src/main/java/com/example/jetsnack/ui/home/Home.kt#L130 this?
    NavHost(
        navController = navController,
        startDestination = if (isSignIn) NavigationDestinations.MainGraph else NavigationDestinations.AuthGraph
    ) {
        // Auth
        composable<NavigationDestinations.AuthGraph> {
            AuthNavGraph(
                navController = navController,
                supabaseClient = supabaseClient
            )
        }

        // Main

        composable<NavigationDestinations.Main> {
            MainScreen(navController = navController)
        }

        // Ai Chat
        composable<NavigationDestinations.AiChat> {
            AiChatScreen()
        }

        // Notification
        composable<NavigationDestinations.Notification> {
            NotificationScreen()
        }

        // WARN: Watch out if the nested in MainGraph can navigate out to this?
        composable<NavigationDestinations.CreateSourceGraph> {
            CreateSourceGraph(navController = navController)
        }

        composable<NavigationDestinations.TopUpGraph> {
            TopUpGraph(navController = navController)
        }

        composable<NavigationDestinations.CreateTicketGraph> {
            TopUpGraph(navController = navController)
        }
    }
}
