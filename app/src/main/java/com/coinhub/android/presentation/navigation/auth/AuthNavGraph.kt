package com.coinhub.android.presentation.navigation.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.presentation.auth.AuthScreen
import com.coinhub.android.presentation.confirm_auth.ConfirmAccountScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.profile.ProfileScreen
import io.github.jan.supabase.SupabaseClient

@Composable
fun AuthNavGraph(
    supabaseClient: SupabaseClient,
    supabaseService: SupabaseService,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavDestinations.Auth) {
        composable<AppNavDestinations.Auth> {
            AuthScreen(
                onSignedIn = {
                    supabaseService.setIsUserSignedIn(true)
                },
                onSignedUp = { navController.navigate(AppNavDestinations.ConfirmAccount) },
                supabaseClient = supabaseClient
            )
        }

        composable<AppNavDestinations.ConfirmAccount> {
            // TODO: Button to check and navigate to CreateProfileScreen
            ConfirmAccountScreen()
        }

        composable<AppNavDestinations.CreateProfile> {
            ProfileScreen(
                onSuccess = {
                    // TODO: Call the set isSign to true to call the flow change
                }
            )
        }
    }
}