package com.kidsharu.kidsharu.other

import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kidsharu.kidsharu.model.Album
import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val rm = remoteMessage ?: return

        remoteMessage.notification?.let { noti ->
            val title = noti.title ?: return@let
            val content = noti.body ?: return@let

            // TODO channel and 1234
            NotificationUtil.notify(this, MyNotificationChannel.AlbumProcessDone, 1234, title, content)
        }

        val message = rm.data["message"]
        val data =rm.data["data"]

        when (message) {
            "album_modified" -> {
                val album = Album(JSONObject(data))
                Handler(Looper.getMainLooper()).post {
                    EventBus.main.post(AlbumModifyEvent(album))
                }
            }
            else -> {

            }
        }
    }

    override fun onNewToken(token: String?) {
//        System.out.println("Refreshed token: $token")
    }
}