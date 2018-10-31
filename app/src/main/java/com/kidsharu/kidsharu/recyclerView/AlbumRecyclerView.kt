package com.kidsharu.kidsharu.recyclerView

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.*
import com.kidsharu.kidsharu.view.AlbumView
import com.squareup.otto.Subscribe

class AlbumRecyclerView : RecyclerView, View.OnAttachStateChangeListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        addOnAttachStateChangeListener(this)
        adapter = AlbumRecyclerAdapter(emptyArray())
        layoutManager = GridLayoutManager(context, 2)
        addItemDecoration(GridSpacingItemDecoration(
                spanCount = 2,
                verticalSpacing = resources.getDimension(R.dimen.album_recycler_view_vertical_spacing).toInt(),
                horizontalSpacing = resources.getDimension(R.dimen.album_recycler_view_horizontal_spacing).toInt(),
                includeEdge = true
        ))
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (this.adapter != null) {
            EventBus.main.unregister(this.adapter)
        }

        super.setAdapter(adapter)

        EventBus.main.register(this.adapter)
    }

    override fun onViewAttachedToWindow(p0: View?) {

    }

    override fun onViewDetachedFromWindow(p0: View?) {
        EventBus.main.unregister(adapter)
    }
}

class AlbumRecyclerAdapter(
        private var albums: Array<Album>,
        private val albumPictures: SparseArray<Array<Picture>> = SparseArray()
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

        if (albumPictures.indexOfKey(position) >= 0) {
            view.setAlbumPictures(albumPictures[position])
        } else {
            // TODO 뭔가 중복되어서 호출 되는 것 같은데
            ServerClient.albumPictureList(album.albumId) { pictures, errMsg ->
                if (errMsg != null) {
                    CrashUtil.onServerError(errMsg)
                    return@albumPictureList
                }

                albumPictures.setValueAt(position, pictures)
                view.setAlbumPictures(pictures)
            }
        }
    }

    @Subscribe
    fun onAlbumAdded(event: AlbumAddEvent) {
        val album = event.album
        val newAlbums = Array(albums.size + 1) { index ->
            if (index < albums.size)
                albums[index]
            else
                album
        }
        this.albums = newAlbums
        notifyItemInserted(albums.size)
    }

    @Subscribe
    fun onAlbumModified(event: AlbumModifyEvent) {
        val album = event.album
        val index = albums.indexOfFirst { it.albumId == album.albumId }
        if (index >= 0) {
            albums[index] = album
            notifyItemChanged(index)
        }
    }
}

class AlbumPreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)