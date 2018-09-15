package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.recyclerView.PictureRecyclerAdapter
import kotlinx.android.synthetic.main.activity_album_detail.*
import kotlinx.android.synthetic.main.toolbar_album_detail.*

class AlbumDetailActivity: AppCompatActivity() {
    companion object {
        const val ALBUM_INTENT_KEY = "album"
        const val PICTURES_INTENT_KEY = "pictures"
    }

    private lateinit var album: Album
    private lateinit var pictures: Array<Picture>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        album = intent.getParcelableExtra(ALBUM_INTENT_KEY)
        pictures = intent.getParcelableArrayExtra(PICTURES_INTENT_KEY).map { it as Picture }.toTypedArray()

        title_label.text = album.title
        date_label.text = album.date
        children_num_label.text = String.format(getString(R.string.number_of_people), 0)
        album_recycler_view.adapter = PictureRecyclerAdapter(pictures)
        album_recycler_view.layoutManager = GridLayoutManager(this, 3)
        album_recycler_view.addItemDecoration(GridSpacingItemDecoration(3, 4, false))
    }
}