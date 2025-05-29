package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.auth.AuthScreen
import com.coinhub.android.presentation.confirm_auth.ConfirmAccountScreen
import com.coinhub.android.presentation.create_profile.CreateProfileScreen
import io.github.jan.supabase.SupabaseClient

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    supabaseClient: SupabaseClient,
) {
    NavHost(navController = navController, startDestination = NavigationDestinations.Auth) {
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
}