package com.kidsharu.kidsharu.other

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.R.string.id

object NotificationUtil {
    fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannelList = notificationManager.notificationChannels

            MyNotificationChannel.values().forEach { myChannel ->
                if (notificationChannelList.none { it.id == myChannel.id }) {
                    val channel = NotificationChannel(myChannel.id, myChannel.channelName, myChannel.importance)
                    channel.setSound(myChannel.sound, myChannel.audioAttr)
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }

    fun notifyImageUploadProgress(context: Context,
                                  id: Int,
                                  now: Int,
                                  max: Int) {

        val channel = MyNotificationChannel.ImageUpload
        val content = "($now/$max)"

        val title: String
        val smallIcon: Int
        if (now != max) {
            title = "이미지 업로드 중입니다..."
            smallIcon = R.drawable.ic_file_upload_white_24dp
        } else {
            title = "이미지 업로드 완료"
            smallIcon = R.drawable.ic_done_white_24dp
        }

        val notification = NotificationCompat.Builder(context, channel.id).apply {
            setContentTitle(title)
            setContentText(content)
            setSmallIcon(smallIcon)
            setProgress(max, now, false)
            if (now != max)
                setSound(null)
            else
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        }.build()

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
            notify(id, notification)
        }
    }

    fun notify(context: Context,
               channel: MyNotificationChannel,
               id: Int,
               title: String,
               content: String) {
        val notification = NotificationCompat.Builder(context, channel.id).apply {
            setContentTitle(title)
            setContentText(content)
        }.build()

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
            notify(id, notification)
        }
    }
}

enum class MyNotificationChannel {
    ImageUpload,
    AlbumProcessDone;

    val id: String
        get() = when (this) {
            ImageUpload -> "image_upload"
            AlbumProcessDone -> "album_process_done"
        }

    val channelName: String
        get() = when (this) {
            ImageUpload -> "이미지 업로드"
            AlbumProcessDone -> "앨범 처리 완료"
        }

    val importance: Int
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                when (this) {
                    ImageUpload -> NotificationManager.IMPORTANCE_DEFAULT
                    AlbumProcessDone -> NotificationManager.IMPORTANCE_DEFAULT
                }
            else
                -1

    val sound: Uri?
        get() = when (this) {
            ImageUpload -> null
            AlbumProcessDone -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

    val audioAttr: AudioAttributes?
        get() = when (this) {
            ImageUpload -> null
            AlbumProcessDone ->
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .build()
        }
}