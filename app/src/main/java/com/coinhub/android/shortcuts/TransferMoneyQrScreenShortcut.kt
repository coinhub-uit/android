package com.coinhub.android.shortcuts

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.coinhub.android.MainActivity
import com.coinhub.android.R
import com.coinhub.android.presentation.navigation.AppNavDestinations

object TransferMoneyQrScreenShortcut {
    private const val SHORTCUT_ID = "transferMoneyScreenDynamicShortcut"
    private const val SHORTCUT_LABEL = "Transfer money"
    private const val SHORTCUT_LONG_LABEL = "Transfer money QR"
    private val shortcutIcon = R.drawable.baseline_qr_code_scanner_24

    fun add(
        context: Context,
        mainActivity: Class<out MainActivity>,
    ) {
        val shortcut = ShortcutInfoCompat.Builder(context, SHORTCUT_ID).setShortLabel(SHORTCUT_LABEL)
            .setLongLabel(SHORTCUT_LONG_LABEL).setIcon(
                IconCompat.createWithResource(
                    context, shortcutIcon
                )
            ).setIntent(
                Intent(
                    context, mainActivity
                ).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("destination", AppNavDestinations.TransferMoneyQrGraph.toString())
                }).build()

        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
    }

    fun remove(context: Context) {
        ShortcutManagerCompat.removeDynamicShortcuts(context, listOf(SHORTCUT_ID))
    }
}