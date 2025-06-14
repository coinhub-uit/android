package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.credential_change.CredentialChangeScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.animations.slideInFromRight
import com.coinhub.android.presentation.navigation.animations.slideOutFromLeft
import com.coinhub.android.presentation.profile.ProfileScreen
import com.coinhub.android.presentation.set_pin.SetPinScreen
import com.coinhub.android.presentation.settings.SettingsScreen

fun NavGraphBuilder.menuNavGraph(navController: NavHostController) {
    composable<AppNavDestinations.Settings>(
        enterTransition = slideInFromRight,
        exitTransition = slideOutFromLeft
    ) {
        SettingsScreen(
            onBack = { navController.navigateUp() },
        )
    }

    composable<AppNavDestinations.ChangePin>(
        enterTransition = slideInFromRight,
        exitTransition = slideOutFromLeft
    ) {
        SetPinScreen(
            onBack = { navController.navigateUp() },
        )
    }

    composable<AppNavDestinations.EditProfile>(
        enterTransition = slideInFromRight,
        exitTransition = slideOutFromLeft
    ) {
        ProfileScreen(
            onBack = { navController.navigateUp() },
        )
    }

    composable<AppNavDestinations.CredentialChange>(
        enterTransition = slideInFromRight,
        exitTransition = slideOutFromLeft
    ) {
        CredentialChangeScreen(
            onBack = { navController.navigateUp() },
        )
    }
}
