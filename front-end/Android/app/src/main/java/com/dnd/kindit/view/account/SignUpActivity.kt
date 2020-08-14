package com.dnd.kindit.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.request.UserSignUpRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
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
            checkNicknameCall.enqueue(object : retrofit2.Callback<CommonResponse> {
                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    // 에러 처리 해야할 부분
                    // 서버와 통신이 이루어 지지도 않았음
                }

                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.result == "success") {
                            Toast.makeText(applicationContext, responseBody.message,Toast.LENGTH_SHORT).show()
                            // 닉네임 변경 불가능 표시하기
                        }
                    } else {
                        // 통신 실패; 2XX 번대가 들어오지 않았다면
                    }
                }
            })
        }

        // 사용자 이메일 요청(회원가입)
        su_btn_req_sign.setOnClickListener {
            val signUpDto = UserSignUpRequest(
                su_edt_email.text.toString(),
                su_edt_nickname.text.toString(),
                su_edt_pass1.text.toString(),
                su_edt_pass2.text.toString()
            )

            val signUpService = RetrofitClient.kindItAccountService()
            val signUpCall = signUpService.signUp(signUpDto)
            signUpCall.enqueue(object : retrofit2.Callback<CommonResponse> {
                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    // 에러 처리 해야할 부분
                    // 서버와 통신이 이루어 지지도 않았음
                    Log.d("test",t.message.toString())
                }

                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Toast.makeText(applicationContext, responseBody?.message,Toast.LENGTH_SHORT).show()

                    } else {
                        // 통신 실패; 2XX 번대가 들어오지 않았다면
                        Log.d("test",response.toString())

                    }
                }
            })
        }

    }

    private fun init() {

    }
}