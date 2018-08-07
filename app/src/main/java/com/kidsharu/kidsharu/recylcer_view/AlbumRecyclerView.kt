package com.kidsharu.kidsharu.recylcer_view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.AlbumPreview
import com.kidsharu.kidsharu.view.AlbumPreviewView

class AlbumRecyclerAdapter(
        private val previews: Array<AlbumPreview>
) : RecyclerView.Adapter<AlbumPreviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPreviewHolder {
        val previewView = AlbumPreviewView(parent.context)
        previewView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return AlbumPreviewHolder(previewView)
    }

    override fun getItemCount(): Int {
        return previews.size
    }

    override fun onBindViewHolder(holder: AlbumPreviewHolder, position: Int) {
        val view = holder.itemView as? AlbumPreviewView ?: return
        val preview = previews[position]

        view.setPreview(preview)
    }
}

class AlbumPreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)