package com.kidsharu.kidsharu.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.dialog.ChildManageDialog
import com.kidsharu.kidsharu.dialog.LoadingDialogHelper
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.CrashUtil
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.recyclerView.ChildrenManageRecyclerAdapter
import kotlinx.android.synthetic.main.activity_children_manage.*
import kotlinx.android.synthetic.main.toolbar_children_manage.*

class ChildrenManageActivity: AppCompatActivity(), ChildManageDialog.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_children_manage)
        ActivityUtil.setStatusBarColor(this, Color.WHITE, true)

        refresh_layout.setOnRefreshListener {
            refresh()
        }

        edit_button.setOnClickListener {
            ChildManageDialog(this).apply {
                callback = this@ChildrenManageActivity
            }.show()
        }

        refresh()
    }

    private fun refresh() {
        ServerClient.teacherChildList { children, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg, this)
                return@teacherChildList
            }

            recycler_view.adapter = ChildrenManageRecyclerAdapter(children)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.addItemDecoration(GridSpacingItemDecoration(1, resources.getDimensionPixelSize(R.dimen.children_manage_recycler_view_spacing), true))
            refresh_layout.isRefreshing = false
        }
    }

    override fun onChildManageInfoUpdated(name: String, contact: String) {
        LoadingDialogHelper.show(this)

        ServerClient.teacherChildAdd(name, contact) { child, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg, this)
                return@teacherChildAdd
            }

            if (child != null) {
                val pos = (recycler_view.adapter as ChildrenManageRecyclerAdapter).insert(child)
                recycler_view.smoothScrollToPosition(pos)
            }

            LoadingDialogHelper.dismiss()
        }
    }
}