package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.view.PictureViewForParent
import com.squareup.picasso.Picasso

class PictureRow4RecyclerView(
        private val pictures: Array<Picture>
) : RecyclerView.Adapter<PictureRow4PreviewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureRow4PreviewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, 88 * 4) // TODO 102 * 4
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setOnClickListener(this)
        return PictureRow4PreviewHolder(imageView)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PictureRow4PreviewHolder, position: Int) {
        val view = holder.itemView as? ImageView ?: return
        val picture = pictures[position]

        view.tag = picture.pictureId
        Picasso.get().load(picture.pictureUrl).into(view)
    }

    override fun onClick(p0: View?) {
        val view = p0 as? ImageView ?: return
        val pictureId = view.tag as Int
        val position = pictures.indexOfFirst { it.pictureId == pictureId }

        ActivityUtil.pictureDetail(view.context, pictures, position)
    }
}

class PictureRow4PreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)