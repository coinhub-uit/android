package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.set_pin.SetPinScreen
import com.coinhub.android.presentation.settings.SettingsScreen

fun NavGraphBuilder.menuNavGraph(navController: NavHostController) {
    composable<AppNavDestinations.Settings> {
        SettingsScreen(
            onBack = { navController.navigateUp() },
        )
    }

    composable<AppNavDestinations.ChangePin> {
        SetPinScreen(
            onBack = { navController.navigateUp() },
        )
    }
}
