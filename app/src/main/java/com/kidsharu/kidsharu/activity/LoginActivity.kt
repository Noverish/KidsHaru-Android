package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ActivityUtil
import com.kidsharu.kidsharu.other.ServerClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener { loginBtnClicked() }
        register_teacher.setOnClickListener { ActivityUtil.registerTeacher(this) }
        register_parent.setOnClickListener { ActivityUtil.registerParent(this) }
    }

    private fun loginBtnClicked() {
        val id = id_field.text.toString()
        val pw = password_field.text.toString()

        if (teacher_radio.isChecked)
            ServerClient.teacherLogin(id, pw) { errMsg ->
                if (errMsg == null) {
                    Toast.makeText(this, "선생 로그인 성공", Toast.LENGTH_SHORT).show()
                }
            }
        else
            ServerClient.parentLogin(id, pw) { errMsg ->
                if (errMsg == null) {
                    Toast.makeText(this, "부모 로그인 성공", Toast.LENGTH_SHORT).show()
                }
            }
    }
}