package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.ImagePreview
import com.kidsharu.kidsharu.other.ActivityUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_image_preview.view.*

class ImagePreviewView: FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var preview: ImagePreview? = null

    init {
        View.inflate(context, R.layout.view_image_preview, this)
    }

    fun setPreview(preview: ImagePreview) {
        this.preview = preview

        Picasso.get().load(preview.url).into(image_view)
    }

    fun getPreview(): ImagePreview? {
        return preview
    }
}