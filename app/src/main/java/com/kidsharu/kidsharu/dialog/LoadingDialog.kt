package com.kidsharu.kidsharu.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ProgressBar
import java.lang.ref.WeakReference

object LoadingDialogHelper {
    private var dialogRef: WeakReference<Dialog>? = null

    fun show(context: Context, cancelHandler: (() -> Unit)? = null): Dialog {
        dismiss()

        val dialog = LoadingDialog(context, cancelHandler)
        dialog.show()
        dialogRef = WeakReference(dialog)
        return dialog
    }

    fun dismiss() {
        dialogRef?.get()?.dismiss()
    }
}

private class LoadingDialog(context: Context,
                            cancelHandler: (() -> Unit)? = null) : Dialog(context) {
    init {
        setCancelable(cancelHandler != null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        progressBar.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        (progressBar.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER

        val layout = FrameLayout(context)
        layout.addView(progressBar)
        layout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        setContentView(layout)

        cancelHandler?.let {
            setOnCancelListener {
                it()
            }
        }
    }
}