package com.kidsharu.kidsharu.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Child
import kotlinx.android.synthetic.main.view_child_select.view.*

class ChildSelectView : FrameLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var child: Child? = null
    var onChildSelectedListener: ChildSelectView.OnChildSelectedListener? = null

    init {
        View.inflate(context, R.layout.view_child_select, this)

        setOnClickListener(this)
    }

    fun setChild(child: Child) {
        this.child = child

        name_label.text = child.name
    }

    override fun onClick(p0: View?) {
        val child = child ?: return
        onChildSelectedListener?.onChildSelected(child)
    }

    interface OnChildSelectedListener {
        fun onChildSelected(child: Child)
    }
}