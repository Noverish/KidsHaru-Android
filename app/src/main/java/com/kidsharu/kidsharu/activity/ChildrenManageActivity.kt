package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.CrashUtil
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recyclerView.ChildrenManageRecyclerAdapter
import kotlinx.android.synthetic.main.activity_children_manage.*

class ChildrenManageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_children_manage)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        ServerClient.teacherChildList { children, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg, this)
                return@teacherChildList
            }

            recycler_view.adapter = ChildrenManageRecyclerAdapter(children)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.addItemDecoration(GridSpacingItemDecoration(1, resources.getDimensionPixelSize(R.dimen.children_manage_recycler_view_spacing), true))
        }
    }
}