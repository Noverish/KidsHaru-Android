package com.kidsharu.kidsharu.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.AlbumStatus
import com.kidsharu.kidsharu.other.*
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

    private lateinit var album: Album

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            title = getStringExtra(TITLE_INTENT_KEY)
            content = getStringExtra(CONTENT_INTENT_KEY)
            paths = getStringArrayExtra(PATHS_INTENT_KEY)
        }

        EventBus.main.register(this)

        thread {
            createAlbumAndUploadPictures()
        }

        return START_REDELIVER_INTENT
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun increaseUploadNum() {
        uploadNum += 1
        album.uploadNumNow = uploadNum
        album.uploadNumMax = paths.size
        handler.post {
            NotificationUtil.notifyImageUploadProgress(this, album.albumId, paths.size, uploadNum)
            EventBus.main.post(AlbumModifyEvent(album))
        }
    }

    private fun createAlbumAndUploadPictures() {
        uploadNum = 0

        ServerClient.teacherAlbumAdd(title, content) { album, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg)
                return@teacherAlbumAdd
            }

            this.album = album ?: return@teacherAlbumAdd
            EventBus.main.post(AlbumAddEvent(album))
            paths.forEach { path -> uploadPicture(album.albumId, path) }
        }
    }

    private fun uploadPicture(albumId: Int, path: String) {
        ServerClient.pictureUpload(albumId, path) { imageUrl, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg)
                increaseUploadNum()
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
                CrashUtil.onServerError(errMsg)
            }

            increaseUploadNum()
            if (uploadNum == paths.size)
                changeAlbumStatus(albumId)
        }
    }

    private fun changeAlbumStatus(albumId: Int) {
        ServerClient.albumModify(albumId, status = AlbumStatus.processing) { errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg)
            }

            album.status = AlbumStatus.processing
            album.uploadNumNow = -1
            album.uploadNumMax = -1
            EventBus.main.post(AlbumModifyEvent(album))
            stopSelf()
        }
    }

    override fun onDestroy() {
        EventBus.main.unregister(this)
        super.onDestroy()
    }
}