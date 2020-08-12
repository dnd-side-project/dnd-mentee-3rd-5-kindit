package com.dnd.kindit.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Response

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
            val checkNicknameService = RetrofitClient.kindItAccountService()
            val checkNicknameCall = checkNicknameService.checkUsableUserNickname(su_edt_nickname.text.toString())
            checkNicknameCall.enqueue(object : retrofit2.Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 에러 처리 해야할 부분
                    // 서버와 통신이 이루어 지지도 않았음
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){

                    }else{
                        // 통신 실패; 2XX 번대가 들어오지 않았다면
                    }
                }
            })
        }
    }

    private fun init() {
    }
}