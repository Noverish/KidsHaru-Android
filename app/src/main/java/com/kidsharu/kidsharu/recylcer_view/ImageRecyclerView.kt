package com.kidsharu.kidsharu.recylcer_view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.ImagePreview
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.view.AlbumPreviewView
import com.kidsharu.kidsharu.view.ImagePreviewView

class ImageRecyclerAdapter(
        private val previews: Array<ImagePreview>
) : RecyclerView.Adapter<ImagePreviewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePreviewHolder {
        val previewView = ImagePreviewView(parent.context)
        previewView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        previewView.setOnClickListener(this)
        return ImagePreviewHolder(previewView)
    }

    override fun getItemCount(): Int {
        return previews.size
    }

    override fun onBindViewHolder(holder: ImagePreviewHolder, position: Int) {
        val view = holder.itemView as? ImagePreviewView ?: return
        val preview = previews[position]

        view.setPreview(preview)
    }

    override fun onClick(p0: View?) {
        val view = p0 as? ImagePreviewView ?: return
        val item = view.getPreview() ?: return
        val position = previews.indexOf(item)

        ActivityUtil.imageDetail(view.context, previews, position)
    }
}

class ImagePreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)