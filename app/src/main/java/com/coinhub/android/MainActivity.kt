package com.coinhub.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.presentation.navigation.auth.AuthNavGraph
import com.coinhub.android.presentation.navigation.app.AppNavGraph
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

    private var isSignedIn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                isSignedIn = supabaseService.isUserSignedIn()
            }
            CoinhubTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (isSignedIn) {
                        true -> {
                            AppNavGraph()
                        }

                        false -> {
                            AuthNavGraph(supabaseClient = supabaseClient)
                        }

                        null -> {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
