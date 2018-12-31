package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.AlbumStatus
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_album.view.*

class AlbumView : FrameLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var album: Album? = null
    private var albumPictures = emptyArray<Picture>()

    init {
        View.inflate(context, R.layout.view_album, this)

        setBackgroundResource(R.drawable.background_corner_6_white)
        elevation = resources.getDimension(R.dimen.album_view_elevation)

        setOnClickListener(this)
    }

    fun setAlbum(album: Album) {
        this.album = album

        title_label.text = album.title
        date_label.text = album.date.split(" ")[0]

        status_label.visibility = if (album.status == AlbumStatus.done) View.GONE else View.VISIBLE

        status_label.text = when(album.status) {
            // TODO %d 로 strings.xml 로 하기
            AlbumStatus.uploading ->
                if (album.uploadNumMax == -1)
                    "업로드 준비 중..."
                else
                    "업로드 중 (${album.uploadNumNow}/${album.uploadNumMax})"
            AlbumStatus.processing -> "처리 중 (${album.checkingPictureNum}/${album.totalPictureNum})"
            AlbumStatus.checking -> "검토 필요"
            else -> ""
        }
    }

    fun setAlbumPictures(albumPictures: Array<Picture>) {
        val oldTop5 = this.albumPictures.take(5)
        val newTop5 = albumPictures.take(5)
        val top5Same = oldTop5.toTypedArray().contentEquals(newTop5.toTypedArray())

        this.albumPictures = albumPictures

        if (!top5Same) {
            val pictureUrls = albumPictures.map { it -> it.pictureUrl }
            pictureUrls.getOrNull(0)?.let { Picasso.get().load(it).into(cover_image_view_1) }
            pictureUrls.getOrNull(1)?.let { Picasso.get().load(it).into(cover_image_view_2) }
            pictureUrls.getOrNull(2)?.let { Picasso.get().load(it).into(cover_image_view_3) }
            pictureUrls.getOrNull(3)?.let { Picasso.get().load(it).into(cover_image_view_4) }
            pictureUrls.getOrNull(4)?.let { Picasso.get().load(it).into(cover_image_view_5) }
        }
    }

    override fun onClick(p0: View?) {
        val album = album ?: return

        ActivityUtil.albumDetail(context, album)
    }
}