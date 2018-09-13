package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.other.ActivityUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_album.view.*

class AlbumView : FrameLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var album: Album? = null

    init {
        View.inflate(context, R.layout.view_album, this)

        setOnClickListener(this)
    }

    fun setAlbum(album: Album) {
        this.album = album

        Picasso.get().load(album.coverImgUrl).into(cover_image_view_1)
        Picasso.get().load(album.coverImgUrl).into(cover_image_view_2)
        Picasso.get().load(album.coverImgUrl).into(cover_image_view_3)
        Picasso.get().load(album.coverImgUrl).into(cover_image_view_4)
        Picasso.get().load(album.coverImgUrl).into(cover_image_view_5)
        title_label.text = album.title
        date_label.text = album.date
    }

    override fun onClick(p0: View?) {
        val album = album ?: return

        ActivityUtil.albumDetail(context, album)
    }
}