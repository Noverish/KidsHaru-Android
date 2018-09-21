package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.ServerClient
import com.kidsharu.kidsharu.view.PictureViewForParent

class PictureRecyclerAdapter(
        private val pictures: Array<Picture>
) : RecyclerView.Adapter<PicturePreviewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturePreviewHolder {
        val previewView = PictureViewForParent(parent.context)
        previewView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        previewView.setOnClickListener(this)
        return PicturePreviewHolder(previewView)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PicturePreviewHolder, position: Int) {
        val view = holder.itemView as? PictureViewForParent ?: return
        val picture = pictures[position]

        view.setPicture(picture)
    }

    override fun onClick(p0: View?) {
        val view = p0 as? PictureViewForParent ?: return
        val picture = view.getPicture() ?: return
        val position = pictures.indexOf(picture)

        if (ServerClient.isTeacher)
            ActivityUtil.teacherPicture(view.context, pictures, position)
        else
            ActivityUtil.pictureDetail(view.context, pictures, position)
    }
}

class PicturePreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)