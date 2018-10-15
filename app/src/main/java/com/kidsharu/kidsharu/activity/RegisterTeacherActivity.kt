package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ServerClient
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

class RegisterTeacherActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = getString(R.string.register_as_teacher)
        invite_code_field.visibility = View.GONE
        register_btn.setOnClickListener { registerBtnClicked() }
    }

    private fun registerBtnClicked() {
        val id = id_field.text.toString()
        val pw = pw_field.text.toString()
        val pwConfirm = pw_confirm_field.text.toString()
        val name = name_field.text.toString()

        if (pw != pwConfirm) {
            Toast.makeText(this, R.string.pw_confirm_not_match, Toast.LENGTH_SHORT).show()
            return
        }

        ServerClient.teacherRegister(id, pw, name) { errMsg ->
            if (errMsg == null) {
                // TODO go to home
                Toast.makeText(this, R.string.register_as_teacher_success, Toast.LENGTH_SHORT).show()
            }
        }
    }
}