package com.coinhub.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.common.UserAppState
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

    // TODO: @NTGNguyen Maybe have a User Service and have this as a flow.
    private var userAppState = UserAppState.LOADING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CoinhubTheme {
                val isUserSignedIn = supabaseService.isUserSignedIn.collectAsStateWithLifecycle().value
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (isUserSignedIn) {
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

    private val biometricLauncher
        get() = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            userAppState = if (result.resultCode == RESULT_OK) {
                UserAppState.SIGNED_IN
            } else {
                UserAppState.FAILED
            }
        }

    private fun launchBiometricAuthentication() {
        val intent = Intent(this, LockActivity::class.java)
        biometricLauncher.launch(intent)
    }

}
