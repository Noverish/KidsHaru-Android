package com.kidsharu.kidsharu.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.kidsharu.kidsharu.R
import kotlinx.android.synthetic.main.activity_album_add.*

class AlbumAddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_add)

        confirm_button.setOnClickListener {
            ImagePicker.create(this).start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            val images = ImagePicker.getImages(data)
            // or get a single image only
            val image = ImagePicker.getFirstImageOrNull(data)

            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}