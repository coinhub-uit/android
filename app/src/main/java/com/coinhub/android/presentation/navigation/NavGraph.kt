package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.screens.auth.AuthScreen
import com.coinhub.android.presentation.screens.auth.ConfirmAccountScreen
import com.coinhub.android.presentation.screens.create_profile.CreateProfileScreen
import com.coinhub.android.presentation.screens.home.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AuthGraph
    ) {
        navigation<AuthGraph>(
            startDestination = Auth,
        ) {
            composable<Auth> {
                AuthScreen(
                    navigateAfterSignedIn = {
                        navController.navigate(Home) {
                            popUpTo(AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    navigateAfterSignedUp = { navController.navigate(ConfirmAccount) }
                )
            }
            composable<ConfirmAccount> {
                ConfirmAccountScreen()
            }
            composable<CreateProfile> {
                CreateProfileScreen()
            }
        }
        navigation<HomeGraph>(
            startDestination = Home
        ) {
            composable<Home> {
                HomeScreen()
            }
        }
    }
}

