package com.kidsharu.kidsharu.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.github.chrisbanes.photoview.PhotoView
import com.kidsharu.kidsharu.model.Face
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.CustomPicassoTarget
import com.kidsharu.kidsharu.other.ServerClient
import com.squareup.picasso.Picasso
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception

class PictureView2 : PhotoView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var isFaceMode: BehaviorSubject<Boolean>? = null

    private var picture: Picture? = null
    private var faces: Array<Face>? = null
    private var originBitmap: Bitmap? = null
    private var facedBitmap: Bitmap? = null

    fun setFaceMode(isFaceMode: BehaviorSubject<Boolean>?) {
        this.isFaceMode = isFaceMode
        isFaceMode?.subscribe {
            refresh()
        }
    }

    fun setPicture(picture: Picture) {
        if (this.picture != picture) {
            faces = null
            originBitmap?.recycle()
            originBitmap = null
            facedBitmap?.recycle()
            facedBitmap = null
        }
        this.picture = picture
        refresh()
    }

    private fun refresh() {
        isFaceMode?.value?.let { isFaceMode ->
            if (isFaceMode) {
                showFacedBitmap()
            } else {
                showOriginBitmap()
            }
        }
    }

    private val picassoTarget = object : CustomPicassoTarget() {
        override fun onFinished(bitmap: Bitmap?, ex: Exception?) {
            this@PictureView2.originBitmap = bitmap
            refresh()
        }
    }

    private fun showOriginBitmap() {
        if (originBitmap == null) {
            val pictureUrl = picture?.pictureUrl ?: return
            Picasso.get().load(pictureUrl).into(picassoTarget)
            return
        }

        this.setImageBitmap(originBitmap)
    }

    private fun showFacedBitmap() {
        if (faces == null) {
            val picture = picture ?: return
            ServerClient.faceList(picture.albumId, picture.pictureId) { faces, errMsg ->
                errMsg?.let { println(it) }
                this@PictureView2.faces = faces
                showFacedBitmap()
            }
            return
        }

        if (originBitmap == null) {
            val pictureUrl = picture?.pictureUrl ?: return
            Picasso.get().load(pictureUrl).into(picassoTarget)
            return
        }

        if (facedBitmap == null) {
            val faces = faces ?: return
            val bitmap = originBitmap ?: return

            val facedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(facedBitmap)

            canvas.drawBitmap(bitmap, 0f, 0f, null)

            val paint = Paint().apply {
                color = Color.WHITE
                strokeWidth = 4f
                style = Paint.Style.STROKE
            }

            for (face in faces) {
                canvas.drawRect(
                        face.rectX.toFloat(),
                        face.rectY.toFloat(),
                        face.rectX + face.rectWidth.toFloat(),
                        face.rectY + face.rectHeight.toFloat(),
                        paint
                )
            }

            this.facedBitmap = facedBitmap
        }

        this.setImageBitmap(facedBitmap)
    }
}