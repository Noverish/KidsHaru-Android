package com.kidsharu.kidsharu.view

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.model.PictureBundleByDate
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.recyclerView.PictureRow4RecyclerView
import kotlinx.android.synthetic.main.view_picture_of_date.view.*

class PictureOfDateView: FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var date: String = ""
    private var pictures: Array<Picture> = emptyArray()

    init {
        View.inflate(context, R.layout.view_picture_of_date, this)
    }

    fun setData(pictureBundleByDate: PictureBundleByDate) {
        this.date = pictureBundleByDate.date
        this.pictures = pictureBundleByDate.pictures

        date_label.text = date
        recycler_view.isNestedScrollingEnabled = false;
        recycler_view.layoutManager = GridLayoutManager(context, 4)
        recycler_view.adapter = PictureRow4RecyclerView(pictureBundleByDate.pictures)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(4, 8, false)) // TODO 8 to dp
    }
}