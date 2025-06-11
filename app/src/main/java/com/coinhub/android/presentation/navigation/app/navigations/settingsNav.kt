package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.settings.SettingsScreen

fun NavGraphBuilder.settingNav(navController: NavHostController) {
    composable<AppNavDestinations.Settings> {
        SettingsScreen(
            onBack = { navController.navigateUp() },
        )
    }
}
