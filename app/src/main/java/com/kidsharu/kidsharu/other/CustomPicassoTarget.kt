package com.kidsharu.kidsharu.other

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

abstract class CustomPicassoTarget: Target {
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        onFinished(null, e)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        onFinished(bitmap, null)
    }

    abstract fun onFinished(bitmap: Bitmap?, ex: Exception?)
}