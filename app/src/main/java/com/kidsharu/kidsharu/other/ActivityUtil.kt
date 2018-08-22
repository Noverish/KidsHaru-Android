package com.kidsharu.kidsharu.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.view.View
import android.view.Window
import com.kidsharu.kidsharu.DummyDatabaseClient
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.activity.*
import com.kidsharu.kidsharu.model.AlbumPreview
import com.kidsharu.kidsharu.model.ImagePreview

object ActivityUtil {

    fun setFullScreen(window: Window) {
        val flags = arrayListOf(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,
                View.SYSTEM_UI_FLAG_FULLSCREEN
        )

        var tmp = window.decorView.systemUiVisibility
        for (flag in flags)
            tmp = tmp or flag
        window.decorView.systemUiVisibility = tmp

        window.decorView.setOnSystemUiVisibilityChangeListener {  visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                Handler().postDelayed({ setFullScreen(window) }, 4000)
            }
        }
    }

    fun registerTeacher(context: Context) {
        context.startActivity(Intent(context, RegisterTeacherActivity::class.java))
    }

    fun registerParent(context: Context) {
        context.startActivity(Intent(context, RegisterParentActivity::class.java))
    }

    fun albumDetail(context: Context,
                    preview: AlbumPreview) {
        DummyDatabaseClient.getAlbumDetail(preview.albumId) { detail ->
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra(AlbumDetailActivity.DETAIL_INTENT_KEY, detail)
            context.startActivity(intent)
        }
    }

    fun albumAdd(context: Context) {
        context.startActivity(Intent(context, AlbumAddActivity::class.java))
    }

    fun imageDetail(context: Context,
                    previews: Array<ImagePreview>,
                    nowPage: Int? = null) {
        val intent = Intent(context, ImageActivity::class.java)
        nowPage?.let {
            intent.putExtra(ImageActivity.POSITION_INTENT_KEY, it)
        }
        intent.putExtra(ImageActivity.IMAGE_INTENT_KEY, ArrayList(previews.toList()))
        context.startActivity(intent)
    }
}