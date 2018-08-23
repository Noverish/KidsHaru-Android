package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.recylcer_view.PictureRecyclerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class AlbumDetailActivity: AppCompatActivity() {
    companion object {
        const val ALBUM_INTENT_KEY = "album"
        const val PICTURES_INTENT_KEY = "pictures"
    }

    lateinit var album: Album
    lateinit var pictures: Array<Picture>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        album = intent.getParcelableExtra(ALBUM_INTENT_KEY)
        pictures = intent.getParcelableArrayExtra(PICTURES_INTENT_KEY).map { it as Picture }.toTypedArray()

        toolbar_title.text = album.title
        Picasso.get().load(album.coverImgUrl).into(album_cover_image_view)
        shared_date_label.text = album.date
//        children_num_label.text = "${album.childrenNum} 명"
        album_recycler_view.adapter = PictureRecyclerAdapter(pictures)
        album_recycler_view.layoutManager = GridLayoutManager(this, 3)
        album_recycler_view.addItemDecoration(GridSpacingItemDecoration(3, 4, false))
    }
}