package com.coinhub.android.widgets.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.coinhub.android.widgets.ui.TicketWidget

class TicketWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TicketWidget()
}