package com.kidsharu.kidsharu.viewPager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.view.PictureViewForTeacher
import io.reactivex.subjects.BehaviorSubject

class PicturePagerAdapter(
        private val pictures: Array<Picture>,
        private val isFaceMode: BehaviorSubject<Boolean>? = null
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return PictureViewForTeacher(container.context).also { imageView ->
            imageView.setPicture(pictures[position])
            imageView.setFaceMode(isFaceMode)
            container.addView(imageView)
        }
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