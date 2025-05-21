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
import com.coinhub.android.authentication.utils.Home
import com.coinhub.android.authentication.utils.Login
import com.coinhub.android.authentication.utils.SignUp
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun AuthenticationNavigation(supabaseViewModel: SupabaseViewModel = viewModel()) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val supabaseGoogleAction = SupabaseClient.client.composeAuth.rememberSignInWithGoogle(
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
        startDestination = if (supabaseViewModel.isUserLoggedIn) Home else Login
    )
    {
        composable<Login> {
            LoginScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context,
                supabaseGoogleAction = supabaseGoogleAction
            )
        }
        composable<Home> {
            HomeScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context
            )
        }
        composable<SignUp> {
            SignupScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context,
                supabaseGoogleAction = supabaseGoogleAction
            )
        }
    }
}

