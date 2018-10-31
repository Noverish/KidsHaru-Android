package com.kidsharu.kidsharu.other

import android.app.Application
import com.squareup.otto.Bus

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtil.init(this)
    }
}