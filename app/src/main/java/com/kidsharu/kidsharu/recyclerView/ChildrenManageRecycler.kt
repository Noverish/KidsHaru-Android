package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Child
import com.kidsharu.kidsharu.view.ChildrenManageView

class ChildrenManageRecyclerAdapter(
        private var children: Array<Child>
) : RecyclerView.Adapter<ChildrenManageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenManageViewHolder {
        val view = ChildrenManageView(parent.context)
        view.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ChildrenManageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ChildrenManageViewHolder, position: Int) {
        val view = holder.itemView as? ChildrenManageView ?: return
        val child = children[position]

        view.setChild(child)
    }

    fun insert(child: Child): Int {
        val newChildren = Array(children.size + 1) { index ->
            if (index < children.size)
                children[index]
            else
                child
        }
        this.children = newChildren

        notifyItemInserted(newChildren.size)

        return newChildren.size
    }
}

class ChildrenManageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)