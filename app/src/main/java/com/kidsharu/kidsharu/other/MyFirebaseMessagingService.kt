package com.kidsharu.kidsharu.other

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kidsharu.kidsharu.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val rm = remoteMessage ?: return

        remoteMessage.notification?.let { noti ->
            val title = noti.title ?: return@let
            val content = noti.body ?: return@let

            // TODO channel and 1234
            NotificationUtil.notify(this, MyNotificationChannel.AlbumProcessDone, 1234, title, content)
        }



//        val data = rm.data
//
//
//
//        val albumId = data["album_id"]!!.toInt()
//        val string = data["string"]!!.toString()
//
//        val intent = Intent()
//        intent.putExtra("album_id", albumId)
//        intent.putExtra("string", string)
//        intent.action = "com.kidsharu.kidsharu.action"
//        sendBroadcast(intent)
    }

    override fun onNewToken(token: String?) {
//        System.out.println("Refreshed token: $token")
    }
}