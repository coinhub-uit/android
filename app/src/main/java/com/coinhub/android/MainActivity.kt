package com.coinhub.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.presentation.navigation.NavGraph
import com.coinhub.android.ui.theme.CoinhubTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var supabaseClient: SupabaseClient

    @Inject
    lateinit var supabaseService: SupabaseService

    private var isSignedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            LaunchedEffect(Unit) {
                isSignedIn = supabaseService.isUserSignedIn()
            }
            CoinhubTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavGraph(isSignIn = isSignedIn, supabaseClient = supabaseClient)
                    }
                }
            }
        }
    }
}
