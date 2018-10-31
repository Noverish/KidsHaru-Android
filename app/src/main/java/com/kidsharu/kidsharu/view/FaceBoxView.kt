package com.kidsharu.kidsharu.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.dialog.ChildSelectDialog
import com.kidsharu.kidsharu.dialog.LoadingDialogHelper
import com.kidsharu.kidsharu.model.Child
import com.kidsharu.kidsharu.model.Face
import com.kidsharu.kidsharu.other.CrashUtil
import com.kidsharu.kidsharu.other.ServerClient
import kotlin.properties.Delegates

class FaceBoxView : LinearLayout, View.OnClickListener, ChildSelectDialog.OnChildSelectedListener {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var faceBox = View(context)
    var nameLabel = TextView(context)

    var face: Face? by Delegates.observable<Face?>(null) { property, old, new ->
        val name = new?.childName?.takeIf { it != "" }
        if (name == null) {
            nameLabel.text = "???"
            nameLabel.setTextColor(Color.WHITE)
            nameLabel.setBackgroundColor(Color.RED)
            faceBox.setBackgroundResource(R.drawable.background_view_face_box_red)
        } else {
            nameLabel.text = name
            nameLabel.setTextColor(Color.BLACK)
            nameLabel.setBackgroundColor(Color.WHITE)
            faceBox.setBackgroundResource(R.drawable.background_view_face_box_white)
        }
    }

    init {
        orientation = VERTICAL
        setOnClickListener(this)

        faceBox.setBackgroundResource(R.drawable.background_view_face_box_white)
        faceBox.layoutParams = LinearLayout.LayoutParams(0, 0)
        addView(faceBox)

        nameLabel.setBackgroundColor(Color.WHITE)
        nameLabel.setTextColor(Color.BLACK)
        nameLabel.layoutParams = LinearLayout.LayoutParams(0, 0)
        nameLabel.gravity = Gravity.CENTER
        nameLabel.includeFontPadding = false
        addView(nameLabel)
    }

    override fun onClick(p0: View?) {
        val face = face ?: return

        ServerClient.teacherChildList { children, errMsg ->
            if (errMsg != null) {
                CrashUtil.onServerError(errMsg, context)
                return@teacherChildList
            }

            ChildSelectDialog(context, children, face.childId).apply {
                onChildSelectedListener = this@FaceBoxView
            }.show()
        }
    }

    override fun onChildSelected(child: Child) {
        val face = face ?: return

        LoadingDialogHelper.show(context)
        ServerClient.faceModify(face.faceId, child.childId) { errMsg1 ->
            if (errMsg1 != null) {
                CrashUtil.onServerError(errMsg1, context)
                LoadingDialogHelper.dismiss()
                return@faceModify
            }

            ServerClient.faceDetail(face.faceId) { newFace, errMsg2 ->
                if (errMsg2 != null) {
                    CrashUtil.onServerError(errMsg2, context)
                } else {
                    this.face = newFace
                    this.nameLabel.text = child.name
                }

                LoadingDialogHelper.dismiss()
            }
        }
    }
}
