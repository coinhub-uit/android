package com.coinhub.android.data.remote

import android.util.Log
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
            Log.d(
                "dgauiwdghwankjd", "${
                    remoteMessage
                        .notification
                }"
            )
            //TODO:Handle the notification here, later
        }
    }
}