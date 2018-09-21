package com.kidsharu.kidsharu.view

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.github.chrisbanes.photoview.OnMatrixChangedListener
import com.github.chrisbanes.photoview.PhotoView
import com.kidsharu.kidsharu.model.Face
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.CrashUtil
import com.kidsharu.kidsharu.other.ServerClient
import com.squareup.picasso.Picasso
import io.reactivex.subjects.BehaviorSubject


// TODO Change this class name
class PictureViewForTeacher : FrameLayout, OnMatrixChangedListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val imageView = PhotoView(context).apply { layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT) }
    private val faceBoxLayout = FrameLayout(context).apply { layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT) }

    private var isFaceMode: BehaviorSubject<Boolean>? = null
    private var imageMatrix = FloatArray(9)

    private var picture: Picture? = null
    private var faceBoxes: Array<FaceBoxView>? = null

    init {
        addView(imageView)
        addView(faceBoxLayout)

        imageView.setOnMatrixChangeListener(this)
    }

    fun setFaceMode(isFaceMode: BehaviorSubject<Boolean>?) {
        this.isFaceMode = isFaceMode
        isFaceMode?.subscribe {
            refresh()
        }
    }

    fun setPicture(picture: Picture) {
        if (this.picture != picture) {
            faceBoxes = null
            imageView.setImageDrawable(null)
            Picasso.get().load(picture.pictureUrl).into(imageView)
            this.picture = picture
        }
        refresh()
    }

    private fun refresh() {
        isFaceMode?.value?.let { isFaceMode ->
            if (isFaceMode) {
                showFaceBox()
            } else {
                hideFaceBox()
            }
        }
    }

    private fun showFaceBox() {
        if (faceBoxes == null) {
            val picture = picture ?: return
            ServerClient.faceList(picture.albumId, picture.pictureId) { faces, errMsg ->
                if (errMsg != null) {
                    CrashUtil.onServerError(context, errMsg)
                    return@faceList
                }

                faceBoxes = Array(faces.size) { i ->
                    val face = faces[i]

                    FaceBoxView(context).also { it ->
                        it.face = face
                        it.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    }
                }

                showFaceBox()
            }
        }

        faceBoxLayout.removeAllViews()
        val faceBoxes = faceBoxes ?: return
        for (faceBox in faceBoxes) {
            transform(faceBox)
            faceBoxLayout.addView(faceBox)
        }
    }

    private fun hideFaceBox() {
        faceBoxLayout.removeAllViews()
    }

    override fun onMatrixChanged(rect: RectF?) {
        faceBoxes?.forEach { transform(it) }
    }

    private fun transform(faceBox: FaceBoxView) {
        val face = faceBox.face ?: return
        imageView.imageMatrix.getValues(imageMatrix)

        // TODO 행렬로 멋있게 하기
        val newX = face.rectX * imageMatrix[Matrix.MSCALE_X] + imageMatrix[Matrix.MTRANS_X]
        val newY = face.rectY * imageMatrix[Matrix.MSCALE_Y] + imageMatrix[Matrix.MTRANS_Y]
        val faceBoxWidth = face.rectWidth * imageMatrix[Matrix.MSCALE_X]
        val faceBoxHeight = face.rectHeight * imageMatrix[Matrix.MSCALE_Y]
        val nameLabelWidth = faceBoxWidth
        val nameLabelHeight = faceBoxWidth / 3f
        val nameLabelTextSize = faceBoxWidth / 3.3f

        // TODO 이거 FaceBoxView 에 넣을 건 넣기
        (faceBox.layoutParams as FrameLayout.LayoutParams).let { lp ->
            lp.leftMargin = newX.toInt()
            lp.topMargin = newY.toInt()
        }
        (faceBox.faceBox.layoutParams as LinearLayout.LayoutParams).let { lp ->
            lp.width = faceBoxWidth.toInt()
            lp.height = faceBoxHeight.toInt()
        }
        (faceBox.nameLabel.layoutParams as LinearLayout.LayoutParams).let { lp ->
            lp.width = nameLabelWidth.toInt()
            lp.height = nameLabelHeight.toInt()
        }
        faceBox.nameLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, nameLabelTextSize)

//        println("face.rectX ${face.rectX}")
//        println("face.rectY ${face.rectY}")
//        println("face.rectWidth ${face.rectWidth}")
//        println("face.rectHeight ${face.rectHeight}")
//
//        println("Matrix.MSCALE_X ${imageMatrix[Matrix.MSCALE_X]}")
//        println("Matrix.MSCALE_Y ${imageMatrix[Matrix.MSCALE_Y]}")
//        println("Matrix.MTRANS_X ${imageMatrix[Matrix.MTRANS_X]}")
//        println("Matrix.MTRANS_Y ${imageMatrix[Matrix.MTRANS_Y]}")
//
//        println("newWidth $newWidth")
//        println("newHeight $newHeight")
//        println("newX $newX")
//        println("newY $newY")

        faceBox.faceBox.requestLayout()
        faceBox.nameLabel.requestLayout()
    }
}