package com.kidsharu.kidsharu.other

import android.content.Context
import android.widget.Toast

object CrashUtil {
    fun onServerError(errMsg: String, context: Context? = null) {
        println("<SERVER ERROR> $errMsg")

        if (context != null) {
            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
        }
    }
}