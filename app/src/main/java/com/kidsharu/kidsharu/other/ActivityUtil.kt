package com.kidsharu.kidsharu.other

import android.content.Context
import android.content.Intent
import com.kidsharu.kidsharu.DummyDatabaseClient
import com.kidsharu.kidsharu.activity.AlbumDetailActivity
import com.kidsharu.kidsharu.model.AlbumPreview

object ActivityUtil {
    fun albumDetail(context: Context,
                    preview: AlbumPreview) {
        DummyDatabaseClient.getAlbumDetail(preview.albumId) { detail ->
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra(AlbumDetailActivity.DETAIL_INTENT_KEY, detail)
            context.startActivity(intent)
        }
    }
}