package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.profile.ProfileScreen

fun NavGraphBuilder.editProfileNav(navController: NavHostController) {
    composable<AppNavDestinations.EditProfile> {
        ProfileScreen(
            onProfileCreated = { navController.navigateUp() })
    }
}
