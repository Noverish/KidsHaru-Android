package com.kidsharu.kidsharu.recyclerView

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.other.GridSpacingItemDecoration
import com.kidsharu.kidsharu.view.AlbumView

class AlbumRecyclerView : RecyclerView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        adapter = AlbumRecyclerAdapter(emptyArray())
        GridLayoutManager(context, 2)
        addItemDecoration(GridSpacingItemDecoration(
                spanCount = 2,
                verticalSpacing = resources.getDimension(R.dimen.album_recycler_view_vertical_spacing).toInt(),
                horizontalSpacing = resources.getDimension(R.dimen.album_recycler_view_horizontal_spacing).toInt(),
                includeEdge = true
        ))
    }
}

class AlbumRecyclerAdapter(
        private val albums: Array<Album>
) : RecyclerView.Adapter<AlbumPreviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPreviewHolder {
        val previewView = AlbumView(parent.context)
        previewView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return AlbumPreviewHolder(previewView)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumPreviewHolder, position: Int) {
        val view = holder.itemView as? AlbumView ?: return
        val album = albums[position]

        view.setAlbum(album)
    }
}

class AlbumPreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)