package com.dnd.kindit.view.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {

//    private val sessionCallback = SessionCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        init()

        initListener()
    }


    private fun init() { // 데이터 관련 초기화 함수

    }

    private fun initListener() { // 리스너 초기화 함수
        // 로그인 버튼 눌렀을 경우
        login_btn_login.setOnClickListener {
            startActivity(Intent(this, KindItLoginActivity::class.java))
        }

        // 회원 가입을 눌렀을 경우
        login_tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // 소셜로 로그인 버튼
        login_btn_social.setOnClickListener {
            Snackbar.make(l_ll_main, "제작중인 서비스입니다 TT", Snackbar.LENGTH_SHORT).setAction("확인"){
            }.show()
        }
    }
}

/*
        val session = Session.getCurrentSession()
        session.addCallback(sessionCallback)


        btn_custom_login.setOnClickListener {
            session.open(AuthType.KAKAO_LOGIN_ALL, this@LoginActivity)
        }

        btn_custom_login_out.setOnClickListener {
            UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                override fun onCompleteLogout() {
                    Toast.makeText(this@LoginActivity, "로그아웃", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

 */