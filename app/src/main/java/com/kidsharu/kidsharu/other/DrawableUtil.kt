package com.kidsharu.kidsharu.other

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

object DrawableUtil {
    fun createDrawable(corner: Float = 0f,
                       backgroundColor: Int = Color.WHITE): Drawable {
        val d = GradientDrawable()

        d.cornerRadius = corner
        d.setColor(backgroundColor)

        return d
    }
}