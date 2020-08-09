package com.dnd.kindit.view.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.activity_login.*


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
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        // 회원 가입을 눌렀을 경우
        login_tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
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