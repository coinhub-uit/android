package com.coinhub.android.presentation.navigation.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.auth.AuthScreen
import com.coinhub.android.presentation.confirm_auth.ConfirmAccountScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.profile.ProfileScreen
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun AuthNavGraph(
    destinationFlow: SharedFlow<String>,
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        destinationFlow.collect {
            navController.navigate(AppNavDestinations.Auth) {
                popUpTo(AppNavDestinations.Auth) {
                    inclusive = true
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = AppNavDestinations.Auth) {
        composable<AppNavDestinations.Auth> {
            AuthScreen(
                onSignedUpWithCredential = { navController.navigate(AppNavDestinations.ConfirmAccount) },
                onSignedUpWithOAuth = { navController.navigate(AppNavDestinations.CreateProfile) },
            )
        }

        composable<AppNavDestinations.ConfirmAccount> {
            // TODO: Button to check and navigate to CreateProfileScreen
            ConfirmAccountScreen()
        }

        // TODO: Seem this isn't used, remove it?
        composable<AppNavDestinations.CreateProfile> {
            ProfileScreen(
                onBack = null,
            )
        }
    }
}