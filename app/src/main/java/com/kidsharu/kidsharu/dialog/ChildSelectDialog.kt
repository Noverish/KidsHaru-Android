package com.kidsharu.kidsharu.dialog

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Child
import com.kidsharu.kidsharu.recyclerView.ChildSelectRecyclerAdapter
import kotlinx.android.synthetic.main.dialog_child_select.*

class ChildSelectDialog(
        context: Context,
        children: Array<Child>,
        nowSelectedChildId: Int?
) : Dialog(context) {
    var onChildSelectedListener: ChildSelectDialog.OnChildSelectedListener? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_child_select)

        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.adapter = ChildSelectRecyclerAdapter(children, nowSelectedChildId)

        cancel_button.setOnClickListener {
            dismiss()
        }

        confirm_button.setOnClickListener {
            val nowSelectedChild = (recycler_view.adapter as ChildSelectRecyclerAdapter).getNowSelectedChild()
            onChildSelectedListener?.onChildSelected(nowSelectedChild)
            dismiss()
        }
    }

    interface OnChildSelectedListener {
        fun onChildSelected(child: Child)
    }
}
