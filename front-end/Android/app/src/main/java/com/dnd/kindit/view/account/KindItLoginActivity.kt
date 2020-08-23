package com.dnd.kindit.view.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.request.UserLoginRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.UserLoginResponse
import com.dnd.kindit.util.ValidationCheck
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_kindit_login.*
import kotlinx.android.synthetic.main.activity_kindit_login.su_img_back
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Response

class KindItLoginActivity : AppCompatActivity() {

    private val TAG = this.javaClass.toString()
    private lateinit var validationCheck : ValidationCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kindit_login)

        init()

        initListener()
    }

    private fun init(){
        validationCheck = ValidationCheck()
    }

    private fun initListener(){
        // 백 버튼
        su_img_back.setOnClickListener {
            finish()
        }

        // 비밀번호 찾기 클릭 리스너
        kl_tv_find_password.setOnClickListener {
            startActivity(Intent(applicationContext, FindPasswordActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // 로그인 버튼 클릭 리스너
        kl_btn_login.setOnClickListener {

            val email = kl_edt_email.text.toString()
            val password = kl_edt_password.text.toString()

            if(validationCheck.isEmpty(email) && validationCheck.isEmpty(password)){
                val userLoginRequest = UserLoginRequest(email, password)

                val loginService = RetrofitClient.kindItAccountService()
                val loginCall = loginService.login(userLoginRequest)
                loginCall.enqueue(object: retrofit2.Callback<UserLoginResponse>{
                    override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                        Log.e(TAG, t.message.toString())
                        // 에러 처리 해야할 부분
                        // 서버와 통신이 이루어 지지도 않았음
                    }

                    override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody?.result == "success") {
                                // token 저장해야함
                                Log.i(TAG, responseBody.toString())

                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                            }
                        }else{
                            val responseErrorBody = GsonBuilder().create().fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                            Toast.makeText(applicationContext, responseErrorBody?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }else{
                Toast.makeText(applicationContext, "이메일 또는 비밀번호가 비어있을 수 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}