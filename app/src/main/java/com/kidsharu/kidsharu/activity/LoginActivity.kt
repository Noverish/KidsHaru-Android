package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.dialog.LoadingDialogHelper
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.ServerClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "로그인"

        login_button.setOnClickListener { loginBtnClicked() }
        register_teacher.setOnClickListener { ActivityUtil.registerTeacher(this) }
        register_parent.setOnClickListener { ActivityUtil.registerParent(this) }
    }

    private fun loginBtnClicked() {
        val id = id_field.text.toString()
        val pw = pw_field.text.toString()
        LoadingDialogHelper.show(this)

        if (teacher_radio.isChecked)
            ServerClient.teacherLogin(id, pw) { errMsg ->
                LoadingDialogHelper.dismiss()
                if (errMsg != null) {
                    Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
                    return@teacherLogin
                }

                finish()
                ActivityUtil.teacherHome(this)
            }
        else
            ServerClient.parentLogin(id, pw) { errMsg ->
                LoadingDialogHelper.dismiss()
                if (errMsg != null) {
                    Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
                    return@parentLogin
                }

                finish()
                ActivityUtil.parentHome(this)
            }
    }
}