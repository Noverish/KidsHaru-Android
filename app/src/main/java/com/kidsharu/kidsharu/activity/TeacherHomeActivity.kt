package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recyclerView.AlbumRecyclerAdapter
import kotlinx.android.synthetic.main.activity_teacher_home.*
import kotlinx.android.synthetic.main.toolbar.*

class TeacherHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        album_refresh_layout.setOnRefreshListener {
            refresh()
        }

        album_add_btn.setOnClickListener { ActivityUtil.albumAdd(this) }

        refresh()
    }

    private fun refresh() {
        ServerClient.teacherAlbumList { albums, errMsg ->
            if (errMsg != null) {
                println(errMsg)
                return@teacherAlbumList
            }

            album_recycler_view.adapter = AlbumRecyclerAdapter(albums)
            album_refresh_layout.isRefreshing = false
        }
    }
}