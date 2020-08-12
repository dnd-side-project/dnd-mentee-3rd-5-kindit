package com.dnd.kindit.view.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.activity_kindit_login.*

class KindItLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kindit_login)

        init()

        initListener()
    }

    private fun init(){

    }

    private fun initListener(){
        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}