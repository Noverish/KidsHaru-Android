package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ActivityUtil

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ActivityUtil.setFullScreen(window)

        Handler().postDelayed({
            finish()
            ActivityUtil.login(this)
        }, 1500)
    }
}