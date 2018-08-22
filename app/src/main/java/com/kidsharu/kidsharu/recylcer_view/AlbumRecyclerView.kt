package com.kidsharu.kidsharu.recylcer_view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.view.AlbumView

class AlbumRecyclerAdapter(
        private val previews: Array<Album>
) : RecyclerView.Adapter<AlbumPreviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPreviewHolder {
        val previewView = AlbumView(parent.context)
        previewView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return AlbumPreviewHolder(previewView)
    }

    override fun getItemCount(): Int {
        return previews.size
    }

    override fun onBindViewHolder(holder: AlbumPreviewHolder, position: Int) {
        val view = holder.itemView as? AlbumView ?: return
        val preview = previews[position]

        view.setPreview(preview)
    }
}

class AlbumPreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)