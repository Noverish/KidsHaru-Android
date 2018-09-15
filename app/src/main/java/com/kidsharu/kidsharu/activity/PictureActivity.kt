package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.viewPager.PicturePagerAdapter
import kotlinx.android.synthetic.main.activity_image.*

class PictureActivity : AppCompatActivity() {
    companion object {
        const val POSITION_INTENT_KEY = "position"
        const val PICTURES_INTENT_KEY = "previews"
    }

    private var nowPosition = 0
    private lateinit var pictures: Array<Picture>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        ActivityUtil.setFullScreen(this.window)

        nowPosition = intent.getIntExtra(POSITION_INTENT_KEY, 0)
        pictures = intent.getParcelableArrayExtra(PICTURES_INTENT_KEY).map { it as Picture }.toTypedArray()

        total_page_label.text = "${pictures.size}"

        view_pager.adapter = PicturePagerAdapter(pictures)
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