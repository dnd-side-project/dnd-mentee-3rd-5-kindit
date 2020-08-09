package com.dnd.kindit.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()

        initListener()
    }

    private fun initListener() {
        // 닉네임 중복 확인
        su_btn_nickname_valid.setOnClickListener {
            
        }
    }

    private fun init() {
    }
}