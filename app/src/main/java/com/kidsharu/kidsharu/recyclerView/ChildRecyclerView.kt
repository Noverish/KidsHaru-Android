package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Child
import com.kidsharu.kidsharu.view.ChildView

class ChildRecyclerAdapter(
        private val children: Array<Child>,
        private var selectedIndex: Int = 0
) : RecyclerView.Adapter<ChildPreviewHolder>() {
    var itemTouchCallback: ((Child) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildPreviewHolder {
        val childView = ChildView(parent.context)
        childView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        childView.touchCallback = { child -> onItemClicked(child) }
        return ChildPreviewHolder(childView)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ChildPreviewHolder, position: Int) {
        val view = holder.itemView as? ChildView ?: return
        val child = children[position]

        view.setChild(child)
        view.isSelected = position == selectedIndex
    }

    private fun onItemClicked(child: Child) {
        selectedIndex = children.indexOf(child)
        notifyDataSetChanged()
        itemTouchCallback?.invoke(child)
    }
}

class ChildPreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)