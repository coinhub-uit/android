package com.coinhub.android.shortcuts

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.coinhub.android.MainActivity
import com.coinhub.android.R
import com.coinhub.android.presentation.navigation.AppNavDestinations

object TicketScreenShortcut {
    private const val SHORTCUT_ID = "ticketScreenDynamicShortcut"
    private const val SHORTCUT_LABEL = "Tickets"
    private const val SHORTCUT_LONG_LABEL = "Your tickets"
    private val shortcutIcon = R.drawable.baseline_sell_24

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
                    putExtra("destination", AppNavDestinations.Tickets.toString())
                }).build()

        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
    }

    fun remove(context: Context) {
        ShortcutManagerCompat.removeDynamicShortcuts(context, listOf(SHORTCUT_ID))
    }
}