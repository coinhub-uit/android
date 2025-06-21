package com.coinhub.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import com.coinhub.android.presentation.set_pin.SetPinScreen
import com.coinhub.android.shortcuts.TicketScreenShortcut
import com.coinhub.android.shortcuts.TransferMoneyQrScreenShortcut
import com.coinhub.android.ui.theme.CoinhubTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var supabaseService: SupabaseService

    @Inject
    lateinit var themeManager: ThemeManger

    private val _navigationDestination = MutableSharedFlow<String>(replay = 1)
    private val navigationDestination = _navigationDestination.asSharedFlow()

    private val _intent = MutableSharedFlow<Intent>(replay = 1)
    private val intentFlow = _intent.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        processIntent(intent)

        setContent {
            val themeMode = themeManager.themeMode.collectAsStateWithLifecycle().value

            CoinhubTheme(themeMode = themeMode) {
                val isUserSignedIn = supabaseService.isUserSignedIn.collectAsStateWithLifecycle().value
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                ) {
                    when (isUserSignedIn) {
                        SupabaseService.UserAppState.SIGNED_IN -> {
                            AppNavGraph(
                                destinationFlow = navigationDestination,
                                intentFlow = intentFlow,
                            )
                            addSignInShortcuts()
                        }

                        SupabaseService.UserAppState.NOT_SIGNED_IN -> {
                            AuthNavGraph(
                                destinationFlow = navigationDestination,
                            )
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

                        SupabaseService.UserAppState.SET_LOCKED_PIN -> {
                            SetPinScreen()
                        }

                        SupabaseService.UserAppState.FAILED -> {}
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        if (supabaseService.isUserSignedIn.value == SupabaseService.UserAppState.SIGNED_IN) {
            val destination = intent.getStringExtra("destination")
            if (!destination.isNullOrBlank()) {
                _navigationDestination.tryEmit(destination)
            }
            if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
                _intent.tryEmit(intent)
            }
        } else if (supabaseService.isUserSignedIn.value == SupabaseService.UserAppState.NOT_SIGNED_IN) {
            _navigationDestination.tryEmit("") // Yeah trick ;/ like, to trigger the auth graph to navigate to auth screen
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
