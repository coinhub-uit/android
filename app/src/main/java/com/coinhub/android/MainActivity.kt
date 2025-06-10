package com.coinhub.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.domain.managers.ThemeManger
import com.coinhub.android.presentation.lock.LockScreen
import com.coinhub.android.presentation.navigation.app.AppNavGraph
import com.coinhub.android.presentation.navigation.auth.AuthNavGraph
import com.coinhub.android.shortcuts.TicketScreenShortcut
import com.coinhub.android.shortcuts.TransferMoneyQrScreenShortcut
import com.coinhub.android.ui.theme.CoinhubTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var supabaseService: SupabaseService

    @Inject
    lateinit var themeManager: ThemeManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val themeMode = themeManager.themeMode.collectAsStateWithLifecycle().value

            CoinhubTheme(themeMode = themeMode) {
                val isUserSignedIn = supabaseService.isUserSignedIn.collectAsStateWithLifecycle().value
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (isUserSignedIn) {
                        SupabaseService.UserAppState.SIGNED_IN -> {
                            val destination = intent.getStringExtra("destination")
                            AppNavGraph(destination = destination)
                            addSignInShortcuts()
                        }

                        SupabaseService.UserAppState.NOT_SIGNED_IN -> {
                            AuthNavGraph()
                            removeSignInShortcuts()
                        }

                        SupabaseService.UserAppState.LOCKED -> {
                            LockScreen()
                        }

                        SupabaseService.UserAppState.LOADING -> {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.width(32.dp))
                            }
                        }

                        SupabaseService.UserAppState.FAILED -> {}
                    }
                }
            }
        }
    }

    private fun addSignInShortcuts() {
        TicketScreenShortcut.add(context = this, MainActivity::class.java)
        TransferMoneyQrScreenShortcut.add(
            context = this, mainActivity = MainActivity::class.java
        )
    }

    private fun removeSignInShortcuts() {
        TicketScreenShortcut.remove(context = this)
        TransferMoneyQrScreenShortcut.remove(context = this)
    }
}
