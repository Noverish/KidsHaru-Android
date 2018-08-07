package com.kidsharu.kidsharu.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.kidsharu.kidsharu.DummyDatabaseClient
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.recylcer_view.AlbumRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var albumRecyclerAdapter: AlbumRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = AlbumRecyclerAdapter(emptyArray())
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(2, 16, true))

        DummyDatabaseClient.getTeacherAlbumList(0, 0) { previews ->
            albumRecyclerAdapter = AlbumRecyclerAdapter(previews)
            recycler_view.adapter = albumRecyclerAdapter
        }
    }
}
