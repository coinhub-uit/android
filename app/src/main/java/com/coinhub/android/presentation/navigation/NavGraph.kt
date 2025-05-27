package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.auth.AuthScreen
import com.coinhub.android.presentation.confirm_auth.ConfirmAccountScreen
import com.coinhub.android.presentation.create_profile.CreateProfileScreen
import com.coinhub.android.presentation.home.HomeScreen

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
                    onSignedIn = {
                        navController.navigate(HomeGraph) {
                            popUpTo(AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onSignedUp = { navController.navigate(ConfirmAccount) }
                )
            }
            composable<ConfirmAccount> {
                ConfirmAccountScreen()
            }
            composable<CreateProfile> {
                CreateProfileScreen(
                    onProfileCreated = {
                        navController.navigate(HomeGraph)
                    }
                )
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

