package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Child
import com.kidsharu.kidsharu.view.ChildSelectView

class ChildSelectRecyclerAdapter(
        private val children: Array<Child>,
        private var nowSelectedChildId: Int = 0
) : RecyclerView.Adapter<ChildSelectViewHolder>(), ChildSelectView.OnChildSelectedListener {
    var onChildSelectedListener: ChildSelectRecyclerAdapter.OnChildSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildSelectViewHolder {
        val childView = ChildSelectView(parent.context)
        childView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        childView.onChildSelectedListener = this@ChildSelectRecyclerAdapter
        return ChildSelectViewHolder(childView)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ChildSelectViewHolder, position: Int) {
        val view = holder.itemView as? ChildSelectView ?: return
        val child = children[position]

        view.setChild(child)
        view.isSelected = child.childId == nowSelectedChildId
    }

    override fun onChildSelected(child: Child) {
        nowSelectedChildId = child.childId
        notifyDataSetChanged()
        onChildSelectedListener?.onChildSelected(child)
    }

    interface OnChildSelectedListener {
        fun onChildSelected(child: Child)
    }
}

class ChildSelectViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)