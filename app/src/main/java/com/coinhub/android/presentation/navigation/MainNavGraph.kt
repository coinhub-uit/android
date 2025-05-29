package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.main.pages.HomePage
import com.coinhub.android.presentation.main.pages.ProfilePage
import com.coinhub.android.presentation.main.pages.VaultPage

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationDestinations.Main) {
        composable<NavigationDestinations.Home> {
            HomePage()
        }
        composable<NavigationDestinations.Vault> {
            VaultPage()
        }
        composable<NavigationDestinations.Profile> {
            ProfilePage()
        }
    }
}