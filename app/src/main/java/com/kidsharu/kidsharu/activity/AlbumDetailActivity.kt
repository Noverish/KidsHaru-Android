package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.AlbumDetail
import com.kidsharu.kidsharu.model.AlbumPreview
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.recylcer_view.ImageRecyclerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailActivity: AppCompatActivity() {
    companion object {
        const val DETAIL_INTENT_KEY = "detail"
    }

    lateinit var detail: AlbumDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        detail = intent.getParcelableExtra<AlbumDetail>(DETAIL_INTENT_KEY)

        Picasso.get().load(detail.coverImgUrl).into(album_cover_image_view)
        shared_date_label.text = detail.date
        children_num_label.text = "${detail.childrenNum} 명"
        recycler_view.adapter = ImageRecyclerAdapter(detail.imagePreviews)
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(3, 4, false))
    }
}