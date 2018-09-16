package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.model.Picture
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.viewPager.PicturePagerAdapter
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_teacher_picture.*
import kotlinx.android.synthetic.main.toolbar_teacher_picture.*

class TeacherPictureActivity : AppCompatActivity() {
    companion object {
        const val POSITION_INTENT_KEY = "position"
        const val PICTURES_INTENT_KEY = "previews"
    }

    private var nowPosition = 0
    private lateinit var pictures: Array<Picture>
    private var isFaceMode = BehaviorSubject.create<Boolean>().apply { onNext(false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_picture)
        ActivityUtil.setFullScreen(this.window)

        nowPosition = intent.getIntExtra(POSITION_INTENT_KEY, 0)
        pictures = intent.getParcelableArrayExtra(PICTURES_INTENT_KEY).map { it as Picture }.toTypedArray()

        face_btn.setOnClickListener { faceBtnClicked() }
        total_page_label.text = "${pictures.size}"

        view_pager.adapter = PicturePagerAdapter(pictures, isFaceMode)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                now_page_label.text = (position + 1).toString()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        view_pager.currentItem = nowPosition
        now_page_label.text = (nowPosition + 1).toString()
    }

    private fun faceBtnClicked() {
        val isFace = isFaceMode.value?.not() ?: return

        isFaceMode.onNext(isFace)
        face_btn.setImageResource(if (isFace) R.drawable.ic_face_green_24dp else R.drawable.ic_face_white_24dp)
    }
}