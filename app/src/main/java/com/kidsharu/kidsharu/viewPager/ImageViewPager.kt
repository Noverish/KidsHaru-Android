package com.kidsharu.kidsharu.viewPager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.github.chrisbanes.photoview.PhotoView
import com.kidsharu.kidsharu.model.Picture

class PicturePagerAdapter(
        private val pictures: Array<Picture>
) : PagerAdapter() {

    // TODO View holder Pattern
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = PhotoView(container.context)
        container.addView(imageView)
        Picasso.get().load(pictures[position].pictureUrl).into(imageView)
        return imageView
    }

    override fun getCount(): Int {
        return pictures.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}