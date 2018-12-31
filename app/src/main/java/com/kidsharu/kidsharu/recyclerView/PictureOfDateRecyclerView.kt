package com.kidsharu.kidsharu.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kidsharu.kidsharu.model.PictureBundleByDate
import com.kidsharu.kidsharu.view.PictureOfDateView

class PictureOfDateRecyclerAdapter(
        private val pictureBundles: Array<PictureBundleByDate>
) : RecyclerView.Adapter<PictureOfDateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureOfDateViewHolder {
        val view = PictureOfDateView(parent.context)
        view.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return PictureOfDateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pictureBundles.size
    }

    override fun onBindViewHolder(holder: PictureOfDateViewHolder, position: Int) {
        val pictureBundle = pictureBundles[position]
        holder.view.setData(pictureBundle)
    }


}

class PictureOfDateViewHolder(val view: PictureOfDateView) : RecyclerView.ViewHolder(view)

/*

class PictureRow4RecyclerView(
        private val imgUrls: Array<String>
) : RecyclerView.Adapter<PictureRow4PreviewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureRow4PreviewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        imageView.setOnClickListener(this)
        return PictureRow4PreviewHolder(imageView)
    }

    override fun getItemCount(): Int {
        return imgUrls.size
    }

    override fun onBindViewHolder(holder: PictureRow4PreviewHolder, position: Int) {
        val view = holder.itemView as? ImageView ?: return
        val imgUrl = imgUrls[position]

        Picasso.get().load(imgUrl).into(view)
    }

    override fun onClick(p0: View?) {

    }
}

class PictureRow4PreviewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
 */