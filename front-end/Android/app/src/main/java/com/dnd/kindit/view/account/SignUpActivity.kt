package com.dnd.kindit.view.account

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.request.UserSignUpRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.util.ValidationCheck
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    private val TAG = this.javaClass.toString()
    private lateinit var validationCheck : ValidationCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()

        initListener()
    }

    private fun initListener() {
        // editText out of focus 닉네임 중복 확인
        su_edt_nickname.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                val checkNicknameService = RetrofitClient.kindItAccountService()
                val checkNicknameCall = checkNicknameService.checkUsableUserNickname(su_edt_nickname.text.toString())
                checkNicknameCall.enqueue(object : retrofit2.Callback<CommonResponse> {
                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        Log.e(TAG, t.message.toString())
                        // 에러 처리 해야할 부분
                        // 서버와 통신이 이루어 지지도 않았음
                    }

                    override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody?.result == "success") {
                                su_edtl_nickname.helperText = responseBody.message
                                validationCheck.changeNickname = true
                            }
                        }else{
                            val responseErrorBody = GsonBuilder().create().fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                            su_edtl_nickname.helperText = responseErrorBody?.message
                            validationCheck.changeNickname = false
                        }
                    }
                })
            }
        }

        // Password1
        su_edt_pass1.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(validationCheck.checkPasswordRegex(su_edt_pass1.text.toString())){
                    su_edtl_pass1.helperText = "유효한 비밀번호 입니다."
                    validationCheck.changePassword1 = true
                }else{
                    su_edtl_pass1.helperText = "특수기호, 대문자, 숫자가 1개이상 필요합니다."
                    validationCheck.changePassword1 = false
                }
            }
        })
        // Password2
        su_edt_pass2.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(su_edt_pass1.text.toString()== su_edt_pass2.text.toString()){
                    su_edtl_pass2.helperText = "비밀번호가 일치합니다."
                    validationCheck.changePassword2 = true
                }else{
                    su_edtl_pass2.helperText = "비밀번호가 일치하지 않습니다."
                    validationCheck.changePassword2 = false
                }
            }
        })

        // 사용자 이메일 요청(회원가입)
        su_btn_req_sign.setOnClickListener {
            if(validationCheck.checkAllValidation()){
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
                        Log.e(TAG, t.message.toString())
                    }

                    override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            Toast.makeText(applicationContext, responseBody?.message,Toast.LENGTH_LONG).show()
                        }else{
                            val responseErrorBody = GsonBuilder().create().fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                            Toast.makeText(applicationContext, responseErrorBody.message, Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }else{
                Toast.makeText(this.applicationContext, "회원 가입 폼을 다시 확인해주세요.", Toast.LENGTH_LONG).show()
            }
        }

        // 이메일 부분 변경시
        su_edt_email.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(validationCheck.checkEmailRegex(su_edt_email.text.toString())){
                    su_edtl_email.helperText = "유효한 이메일 입니다."
                    validationCheck.changeEmail = true
                }else{
                    su_edtl_email.helperText = "이메일 형식이 맞지 않습니다."
                    validationCheck.changeEmail = false
                }
            }
        })

        // 백 버튼
        su_img_back.setOnClickListener {
            finish()
        }
    }

    private fun init() {
        validationCheck = ValidationCheck()
    }
}