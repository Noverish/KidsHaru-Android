package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.AlbumPreview
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_album_preview.view.*

class AlbumPreviewView: FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var preview: AlbumPreview? = null

    init {
        View.inflate(context, R.layout.view_album_preview, this)
    }

    fun setPreview(preview: AlbumPreview) {
        this.preview = preview

        Picasso.get().load(preview.coverImgUrl).into(cover_image_view)
        title_label.text = preview.title
        date_label.text = preview.date
    }
}