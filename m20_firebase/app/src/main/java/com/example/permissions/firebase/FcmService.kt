package com.example.permissions.firebase

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.permissions.App
import com.example.permissions.R
import com.example.permissions.ui.fragment.gallery.GalleryFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.record_btn)
            .setContentTitle(message.data["nickname"])
            .setContentText(message.data["message"] + " " + convertToDate(message.data["timestamp"]))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(this).notify(GalleryFragment.NOTIFICATION_ID, notification)

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    private fun convertToDate(date: String?): String {
        date ?: return ""
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date(date.toLong() * 1000))

    }


}