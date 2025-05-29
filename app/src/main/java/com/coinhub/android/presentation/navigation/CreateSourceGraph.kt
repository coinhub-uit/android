package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.create_source.CreateSourceScreen

@Composable
fun CreateSourceGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationDestinations.CreateSource) {
        composable<NavigationDestinations.CreateSource> {
            CreateSourceScreen()
        }
    }
}