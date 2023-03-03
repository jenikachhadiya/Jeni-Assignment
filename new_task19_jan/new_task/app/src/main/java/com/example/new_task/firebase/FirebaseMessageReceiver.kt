package com.example.new_task.firebase


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.new_task.activity.SplashActivity
import com.example.new_task.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    //received notification
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "From: ${remoteMessage.data}")

        if (remoteMessage.data.isNotEmpty()){
             Log.d(TAG, "onMessageReceived: ${remoteMessage.data}")
        }
            remoteMessage.notification?.let {
                Log.d(TAG, "Message Notification Body: ${it.body}")
            }
            if (remoteMessage.notification != null) {
                showNotification(
                    remoteMessage.notification!!.title!!,
                    remoteMessage.notification!!.body!!
                )
            }

    }

    private fun customDesign(title: String, message: String): RemoteViews {
        val remoteViews = RemoteViews("com.example.new_task", R.layout.notification_layout)
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        return remoteViews
    }

    private fun showNotification(message: String, title: String) {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(applicationContext, SplashActivity::class.java)
        Log.e(TAG, "showNotification: $title$message")
        Log.d(TAG, "showNotification: $message")
        val channelId = "notification_channel"
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
       // intent.data = Uri.parse("1234")
        intent.putExtra("Title", title)
        intent.putExtra("Message", message)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var customNotification = NotificationCompat.Builder(this, channelId)

            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        // A customized design for the notification can be
        customNotification = customNotification.setContent(customDesign(title, message))

        //create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                channelId,
                "com.example.new_task",
                NotificationManager.IMPORTANCE_HIGH
            )
            //not change after this
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

            customNotification.setChannelId(channelId)
            notificationManager.notify(0, customNotification.build())

        }

    }


}