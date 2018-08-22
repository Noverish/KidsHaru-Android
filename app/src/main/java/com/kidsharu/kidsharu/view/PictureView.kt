package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Picture
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_picture.view.*

class PictureView: FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var picture: Picture? = null

    init {
        View.inflate(context, R.layout.view_picture, this)
    }

    fun setPicture(picture: Picture) {
        this.picture = picture

        Picasso.get().load(picture.pictureUrl).into(image_view)
    }

    fun getPicture(): Picture? {
        return picture
    }
}