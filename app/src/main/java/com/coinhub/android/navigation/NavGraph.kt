package com.coinhub.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.screens.auth.AuthScreen
import com.coinhub.android.presentation.screens.home.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Auth
    ) {
        composable<Auth> {
            AuthScreen()
        }
        composable<Home> {
            HomeScreen()
        }
    }
}

