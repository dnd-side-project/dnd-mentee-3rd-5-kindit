package com.dnd.kindit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        
        // 로딩중 가져올 데이터 가져올 기능
//        startLoading()

        // 임시 로딩 시간
        Handler().postDelayed({
            // 추후 LoginActivity로 이동
            val intent = Intent(this, LoginActivity::class.java)
//            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 2200)
    }
    private fun startLoading(){
        
    }

}