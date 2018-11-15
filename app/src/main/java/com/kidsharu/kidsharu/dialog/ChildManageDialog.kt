package com.kidsharu.kidsharu.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.kidsharu.kidsharu.R
import kotlinx.android.synthetic.main.dialog_child_manage.*

class ChildManageDialog(
        context: Context,
        name: String? = null,
        contact: String? = null
) : Dialog(context) {
    var callback: ChildManageDialog.Callback? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_child_manage)

        name?.let { name_field.setText(it) }
        contact?.let { contact_field.setText(it) }

        cancel_button.setOnClickListener {
            dismiss()
        }

        confirm_button.setOnClickListener {
            val newName = name_field.text.toString()
            val newContact = contact_field.text.toString()

            callback?.onChildManageInfoUpdated(newName, newContact)
            dismiss()
        }
    }

    interface Callback {
        fun onChildManageInfoUpdated(name: String, contact: String)
    }
}
