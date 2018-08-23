package com.kidsharu.kidsharu.other

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.kidsharu.kidsharu.R

// TODO More general 하게 만들기
object NotificationUtil {
    fun progressNotification(context: Context, id: Int, max: Int, progress: Int) {
        val channelId = "album_add"
        val channelName = "앨범 만들기"

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setContentTitle("이미지 업로드 중입니다...")
        builder.setContentText("$progress/$max")
        builder.setSmallIcon(R.drawable.ic_file_upload_white_24dp)
        builder.setProgress(max, progress, false)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, builder.build())
    }

    fun progressNotification2(context: Context, id: Int, max: Int, progress: Int) {
        val channelId = "album_add"
        val channelName = "앨범 만들기"

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setContentTitle("이미지 업로드 완료 ($max/$max)")
//        builder.setContentText("")
        builder.setSmallIcon(R.drawable.ic_done_white_24dp)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, builder.build())
    }
}