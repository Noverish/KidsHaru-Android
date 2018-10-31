package com.kidsharu.kidsharu.other

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val rm = remoteMessage ?: return

        remoteMessage.notification?.let { noti ->
            val title = noti.title ?: return@let
            val content = noti.body ?: return@let

            // TODO channel and 1234
            NotificationUtil.notify(this, MyNotificationChannel.AlbumProcessDone, 1234, title, content)
        }

        val code = rm.data["code"]?.toIntOrNull() ?: return

        if (code == 1) {
            val title = rm.data["title"] ?: return
            val body = rm.data["body"] ?: return
            val albumId = rm.data["album_id"]?.toIntOrNull() ?: return

            ServerClient.albumDetail(albumId) { album, errMsg ->
                if (errMsg != null) {
                    CrashUtil.onServerError(errMsg, this)
                    return@albumDetail
                }

                if (album != null) {
                    NotificationUtil.notify(this, MyNotificationChannel.AlbumProcessDone, 1235, title, body)
                    EventBus.main.post(AlbumModifyEvent(album))
                }
            }
        }
    }

    override fun onNewToken(token: String?) {
//        System.out.println("Refreshed token: $token")
    }
}