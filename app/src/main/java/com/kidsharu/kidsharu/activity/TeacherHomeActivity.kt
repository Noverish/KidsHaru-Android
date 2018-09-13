package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recylcer_view.AlbumRecyclerAdapter
import kotlinx.android.synthetic.main.activity_teacher_home.*
import kotlinx.android.synthetic.main.toolbar.*

class TeacherHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        album_add_btn.setOnClickListener { ActivityUtil.albumAdd(this) }

        album_recycler_view.adapter = AlbumRecyclerAdapter(emptyArray())
        album_recycler_view.layoutManager = GridLayoutManager(this, 2)
        album_recycler_view.addItemDecoration(GridSpacingItemDecoration(2, 12, true))

        ServerClient.teacherAlbumList { albums, errMsg ->
            album_recycler_view.adapter = AlbumRecyclerAdapter(albums)
            errMsg?.let { println(it) }
        }
    }
}