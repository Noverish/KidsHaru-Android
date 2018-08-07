package com.kidsharu.kidsharu.view_pager

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.kidsharu.kidsharu.model.ImagePreview
import com.squareup.picasso.Picasso
import com.github.chrisbanes.photoview.PhotoView

class ImagePagerAdapter(
        private val previews: Array<ImagePreview>
) : PagerAdapter() {

    // TODO View holder Pattern
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = PhotoView(container.context)
        container.addView(imageView)
        Picasso.get().load(previews[position].url).into(imageView)
        return imageView
    }

    override fun getCount(): Int {
        return previews.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}