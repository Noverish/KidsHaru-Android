package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.ImagePreview
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.view_pager.ImagePagerAdapter
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {
    companion object {
        const val POSITION_INTENT_KEY = "position"
        const val IMAGE_INTENT_KEY = "previews"
    }

    var nowPosition = 0
    lateinit var imagePreviews: Array<ImagePreview>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        ActivityUtil.setFullScreen(this.window)

        nowPosition = intent.getIntExtra(POSITION_INTENT_KEY, 0)
        imagePreviews = intent.getParcelableArrayListExtra<ImagePreview>(IMAGE_INTENT_KEY).toTypedArray()

        total_page_label.text = "${imagePreviews.size}"

        view_pager.adapter = ImagePagerAdapter(imagePreviews)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                now_page_label.text = "${position + 1}"
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        view_pager.currentItem = nowPosition
    }
}