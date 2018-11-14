package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Child
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_children_manage.view.*

class ChildrenManageView: FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var child: Child? = null

    init {
        View.inflate(context, R.layout.view_children_manage, this)
    }

    fun setChild(child: Child) {
        this.child = child

        Picasso.get().load(child.profileImg).into(profile_image_view)
        name_label.text = child.name
        contact_label.text = child.contact
    }
}