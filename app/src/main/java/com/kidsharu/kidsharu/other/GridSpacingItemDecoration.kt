package com.kidsharu.kidsharu.other

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Noverish on 2017-01-30.
 * Modified in 2018-09-16.
 * FROM http://stackoverflow.com/a/30701422
 */

class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val verticalSpacing: Int,
        private val horizontalSpacing: Int,
        private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    constructor(spanCount: Int, spacing: Int, includeEdge: Boolean) : this(spanCount, spacing, spacing, includeEdge)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * horizontalSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = verticalSpacing
            }
            outRect.bottom = verticalSpacing // item bottom
        } else {
            outRect.left = column * horizontalSpacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalSpacing // item top
            }
        }
    }
}