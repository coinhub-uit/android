package com.coinhub.android.widgets.ui

import android.content.Context
import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults.defaultTextStyle
import androidx.glance.unit.ColorProvider
import com.coinhub.android.MainActivity
import com.coinhub.android.R
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.utils.toVndFormat
import com.coinhub.android.widgets.repositories.TicketWidgetRepository

class TicketWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val ticketWidgetRepository = TicketWidgetRepository.getInstance(context)
        val totalPrincipalFlow = ticketWidgetRepository.getTotalPrincipal()
        val totalInterestFlow = ticketWidgetRepository.getTotalInterest()

        // Create intent for navigation to ticket screen
        val ticketIntent = Intent(context, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            putExtra("destination", AppNavDestinations.Tickets.toString())
        }

        provideContent {
            val totalPrincipal = totalPrincipalFlow.collectAsState(initial = null).value
            val totalInterest = totalInterestFlow.collectAsState(initial = null).value
            GlanceTheme {
                Scaffold(
                    modifier = GlanceModifier.padding(16.dp).clickable(actionStartActivity(ticketIntent))
                ) {
                    Row(
                        modifier = GlanceModifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = GlanceModifier.size(48.dp).cornerRadius(24.dp).background(
                                color = GlanceTheme.colors.primary.getColor(context)
                            ), contentAlignment = Alignment.Center
                        ) {
                            Image(
                                provider = ImageProvider(R.drawable.round_savings_24),
                                contentDescription = null,
                                modifier = GlanceModifier.size(28.dp),
                                colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary)
                            )
                        }
                        Column(
                            modifier = GlanceModifier.padding(start = 16.dp),
                        ) {
                            Text(
                                totalPrincipal?.toVndFormat() ?: "0 VND",
                                style = defaultTextStyle.copy(
                                    color = ColorProvider(GlanceTheme.colors.primary.getColor(context)),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                    fontWeight = FontWeight.Medium
                                ),
                                maxLines = 1,
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    provider = ImageProvider(R.drawable.round_attach_money_24),
                                    contentDescription = null,
                                    modifier = GlanceModifier.size(16.dp),
                                    colorFilter = ColorFilter.tint(GlanceTheme.colors.onSurfaceVariant)
                                )
                                Text(
                                    totalInterest?.toVndFormat() ?: "0 VND", maxLines = 1,
                                    style = defaultTextStyle.copy(
                                        color = ColorProvider(GlanceTheme.colors.onSurfaceVariant.getColor(context)),
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}