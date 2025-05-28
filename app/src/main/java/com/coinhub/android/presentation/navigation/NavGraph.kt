package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.auth.AuthScreen
import com.coinhub.android.presentation.confirm_auth.ConfirmAccountScreen
import com.coinhub.android.presentation.create_profile.CreateProfileScreen
import com.coinhub.android.presentation.main.HomeScreen
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInState

@Composable
fun NavGraph(
    isSignIn: Boolean,
    supabaseClient: SupabaseClient,
) {
    val navController = rememberNavController()

    // TODO: Split these like,...
    // https://github.com/android/compose-samples/blob/73b3a51e06a6520efb5b4931e71b771d257bf1dd/Jetsnack/app/src/main/java/com/example/jetsnack/ui/home/Home.kt#L130 this?
    NavHost(
        navController = navController, startDestination = NavigationDestinations.AuthGraph
    ) {
        // Auth
        navigation<NavigationDestinations.AuthGraph>(
            startDestination = if (isSignIn) NavigationDestinations.MainGraph else NavigationDestinations.Auth,
        ) {
            composable<NavigationDestinations.Auth> {
                AuthScreen(
                    onSignedIn = {
                        navController.navigate(NavigationDestinations.MainGraph) {
                            popUpTo(NavigationDestinations.AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onSignedUp = { navController.navigate(NavigationDestinations.ConfirmAccount) },
                    supabaseClient = supabaseClient
                )
            }
            composable<NavigationDestinations.ConfirmAccount> {
                ConfirmAccountScreen()
            }
            composable<NavigationDestinations.CreateProfile> {
                CreateProfileScreen(
                    onProfileCreated = {
                        navController.navigate(NavigationDestinations.MainGraph) {
                            popUpTo(NavigationDestinations.AuthGraph) {
                                inclusive = true
                            }

                        }
                    }
                )
            }
        }

        // Main
        navigation<NavigationDestinations.MainGraph>(
            startDestination = NavigationDestinations.Home
        ) {
            composable<NavigationDestinations.Home> {
                HomeScreen()
            }
        }
    }
}
