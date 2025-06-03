package com.coinhub.android

import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.coinhub.android.presentation.lock.LockScreen
import com.coinhub.android.ui.theme.CoinhubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoinhubTheme {
                LockScreen()
            }
        }
    }
}