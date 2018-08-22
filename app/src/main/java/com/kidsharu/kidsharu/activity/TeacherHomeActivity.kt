package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recylcer_view.AlbumRecyclerAdapter
import kotlinx.android.synthetic.main.content_main.*

class TeacherHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)

        recycler_view.adapter = AlbumRecyclerAdapter(emptyArray())
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(2, 16, true))

        ServerClient.teacherAlbumList { albums, errMsg ->
            recycler_view.adapter = AlbumRecyclerAdapter(albums)
            errMsg?.let { println(it) }
        }
    }
}