package com.kidsharu.kidsharu.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kidsharu.kidsharu.R
import com.kidsharu.kidsharu.other.ServerClient
import kotlinx.android.synthetic.main.activity_register.*

class RegisterParentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        name_field.visibility = View.GONE
        register_btn.setOnClickListener { registerBtnClicked() }
    }

    private fun registerBtnClicked() {
        val id = id_field.text.toString()
        val pw = pw_field.text.toString()
        val pwConfirm = pw_confirm_field.text.toString() // TODO pw confirm
        val inviteCode = invite_code_field.text.toString()

        ServerClient.parentRegister(id, pw, inviteCode) { errMsg ->
            if (errMsg == null) {
                Toast.makeText(this, "부모 회원가입 성공", Toast.LENGTH_SHORT).show()
            }
        }
    }
}