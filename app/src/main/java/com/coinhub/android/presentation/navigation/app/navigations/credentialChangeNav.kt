package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.credential_change.CredentialChangeScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.credentialChangeNav(navController: NavHostController) {
    composable<AppNavDestinations.CredentialChange> {
        CredentialChangeScreen(
            onBack = { navController.navigateUp() },
        )
    }
}
