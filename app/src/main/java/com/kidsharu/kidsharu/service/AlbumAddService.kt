package com.kidsharu.kidsharu.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import com.kidsharu.kidsharu.model.AlbumStatus
import com.kidsharu.kidsharu.other.NotificationUtil
import com.kidsharu.kidsharu.other.ServerClient
import kotlin.concurrent.thread

class AlbumAddService : Service() {
    companion object {
        const val TITLE_INTENT_KEY = "title"
        const val CONTENT_INTENT_KEY = "content"
        const val PATHS_INTENT_KEY = "paths"
    }

    private lateinit var title: String
    private lateinit var content: String
    private lateinit var paths: Array<String>
    private var handler = Handler()
    private var uploadNum = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            title = getStringExtra(TITLE_INTENT_KEY)
            content = getStringExtra(CONTENT_INTENT_KEY)
            paths = getStringArrayExtra(PATHS_INTENT_KEY)
        }

        thread {
            println("AlbumAddService.thread")
            createAlbumAndUploadPictures()
        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun increaseUploadNum() {
        uploadNum += 1
        handler.post {
            NotificationUtil.progressNotification(this, 8080, paths.size, uploadNum)
        }
    }

    private fun createAlbumAndUploadPictures() {
        uploadNum = 0

        ServerClient.teacherAlbumAdd(title, content) { album, errMsg ->
            if (errMsg != null) {
                println("AlbumAddService.teacherAlbumAdd $errMsg")
                handler.post { Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show() }
                return@teacherAlbumAdd
            }

            val albumId = album?.albumId ?: return@teacherAlbumAdd
            paths.forEach { path -> uploadPicture(albumId, path) }
        }
    }

    private fun uploadPicture(albumId: Int, path: String) {
        ServerClient.pictureUpload(albumId, path) { imageUrl, errMsg ->
            if (errMsg != null) {
                println("AlbumAddService.pictureUpload $errMsg")
                increaseUploadNum()
                handler.post { Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show() }
                return@pictureUpload
            }

            if (imageUrl != null)
                addPictureToAlbum(albumId, imageUrl)
        }

    }

    private fun addPictureToAlbum(albumId: Int, imageUrl: String) {
        val fileName = imageUrl.split("/").last()
        ServerClient.albumPictureAdd(albumId, fileName) { _, errMsg ->
            if (errMsg != null) {
                println("AlbumAddService.albumPictureAdd $errMsg")
                handler.post { Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show() }
            }

            increaseUploadNum()
            if (uploadNum == paths.size)
                changeAlbumStatus(albumId)
        }
    }

    private fun changeAlbumStatus(albumId: Int) {
        ServerClient.albumModify(albumId, status = AlbumStatus.processing) { errMsg ->
            if (errMsg != null) {
                println("AlbumAddService.albumModify $errMsg")
                handler.post { Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show() }
            }

            handler.post {
                NotificationUtil.progressNotification2(this, 8080, paths.size, uploadNum)
            }

            stopSelf()
        }
    }

    override fun onDestroy() {
        println("SERVICE STOP!!!!!!!")
        super.onDestroy()
    }
}