package com.coinhub.android.data.remote

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.coinhub.android.MainActivity
import com.coinhub.android.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NotificationService", "New token received: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            showNotification(remoteMessage.notification?.title!!, remoteMessage.notification?.body!!)
        }
    }

    private fun showNotification(title: String, body: String) {
        val builder: Notification.Builder? = Notification.Builder(this, "")
            .setSmallIcon(R.drawable.coinhub)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("message", body)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builder?.setContentIntent(pendingIntent)
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder?.build())
    }
}