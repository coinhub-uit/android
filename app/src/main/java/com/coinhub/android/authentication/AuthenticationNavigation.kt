package com.coinhub.android.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.authentication.data.network.SupabaseClient
import com.coinhub.android.authentication.presentation.home.HomeScreen
import com.coinhub.android.authentication.presentation.login.LoginScreen
import com.coinhub.android.authentication.presentation.signup.SignupScreen
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun AuthenticationNavigation(supabaseViewModel: SupabaseViewModel = viewModel()) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val supabaseAction = SupabaseClient.client.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            supabaseViewModel.checkGoogleLoginStatus(
                context,
                result,
                navController = navController
            )
        },
        fallback = {}
    )

    LaunchedEffect(Unit) {
        supabaseViewModel.isUserSignedIn(context)
    }

    NavHost(
        navController = navController,
        startDestination = if (supabaseViewModel.isUserLoggedIn) "home" else "login"
    )
    {
        composable("login") {
            LoginScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context,
                supabaseGoogleAction = supabaseAction
            )
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context
            )
        }
        composable("signup") {
            SignupScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context,
                supabaseGoogleAction = supabaseAction
            )
        }
    }
}

