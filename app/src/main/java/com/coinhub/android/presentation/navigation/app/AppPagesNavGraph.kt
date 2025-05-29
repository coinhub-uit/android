package com.coinhub.android.presentation.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.main.pages.HomePage
import com.coinhub.android.presentation.main.pages.ProfilePage
import com.coinhub.android.presentation.main.pages.VaultPage
import com.coinhub.android.presentation.navigation.AppNavDestinations

@Composable
fun MainPagesNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppNavDestinations.Home) {
        composable<AppNavDestinations.Home> {
            HomePage()
        }
        composable<AppNavDestinations.Vault> {
            VaultPage()
        }
        composable<AppNavDestinations.Profile> {
            ProfilePage()
        }
    }
}
