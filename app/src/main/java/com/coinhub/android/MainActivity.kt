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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    private var isSignedIn: Boolean? = true // TODO: Handle if connect fails? nah use enum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                isSignedIn = supabaseService.isUserSignedIn() // FIXME: Collect state
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
