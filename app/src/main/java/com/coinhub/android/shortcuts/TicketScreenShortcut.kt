package com.coinhub.android.shortcuts

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.coinhub.android.MainActivity
import com.coinhub.android.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TicketScreenShortcut @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val shortcutId = "ticketScreenDynamicShortcut"

    fun addShortcut(
        mainActivity: Class<out MainActivity>,
    ) {
        val shortcut = ShortcutInfoCompat.Builder(context, shortcutId).setShortLabel("Ticket Screen")
            .setLongLabel("Open Your Ticket Screen").setIcon(
                IconCompat.createWithResource(
                    context, R.drawable.baseline_sell_24
                )
            ).setIntent(
                Intent(
                    context, mainActivity
                ).apply {
                    action = Intent.ACTION_VIEW
                }).build()

        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
    }

    fun removeShortcut() {
        ShortcutManagerCompat.removeDynamicShortcuts(context, listOf(shortcutId))
    }
}