package com.kidsharu.kidsharu.other

import android.content.Context
import android.widget.Toast

object CrashUtil {
    fun onServerError(context: Context?, errMsg: String) {
        if (context != null) {
            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
        } else {
            println("onServerError errMsg")
        }
    }
}