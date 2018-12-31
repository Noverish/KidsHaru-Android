package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.PictureBundleByDate
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recyclerView.PictureOfDateRecyclerAdapter
import kotlinx.android.synthetic.main.activity_parent_my_child.*
import java.sql.Array

class ParentMyChildActivity: AppCompatActivity() {
    companion object {
        val CHILD_ID_INTENT_KEY = "child_id"
    }

    private var childId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_my_child)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        childId = intent.getIntExtra(CHILD_ID_INTENT_KEY, -1)

        recycler_view.adapter = PictureOfDateRecyclerAdapter(emptyArray())
        recycler_view.layoutManager = LinearLayoutManager(this)

        ServerClient.childPictureList(childId) { pictures, errMsg ->
            val pictureBundle = PictureBundleByDate(
                    "2018-11-20",
                    pictures
            )

            recycler_view.adapter = PictureOfDateRecyclerAdapter(Array(1) {pictureBundle})
        }
    }
}