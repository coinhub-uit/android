package com.coinhub.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.presentation.navigation.app.AppNavGraph
import com.coinhub.android.presentation.navigation.auth.AuthNavGraph
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinhubTheme {
                val isUserSignedIn = supabaseService.isUserSignedIn.collectAsStateWithLifecycle().value
//                val isUserSignedIn = true
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (isUserSignedIn) {
                        true -> {
                            AppNavGraph(supabaseService = supabaseService)
                        }

                        false -> {
                            AuthNavGraph(supabaseClient = supabaseClient, supabaseService = supabaseService)
                        }

                        null -> {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.width(32.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
