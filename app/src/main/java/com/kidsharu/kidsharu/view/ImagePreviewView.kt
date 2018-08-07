package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.ImagePreview
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_image_preview.view.*

class ImagePreviewView: FrameLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var preview: ImagePreview? = null

    init {
        View.inflate(context, R.layout.view_image_preview, this)

        setOnClickListener(this)
    }

    fun setPreview(preview: ImagePreview) {
        this.preview = preview

        Picasso.get().load(preview.url).into(image_view)
    }

    override fun onClick(p0: View?) {

    }
}