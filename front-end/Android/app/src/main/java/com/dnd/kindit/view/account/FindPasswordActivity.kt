package com.dnd.kindit.view.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.request.EmailRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.util.ValidationCheck
import kotlinx.android.synthetic.main.activity_find_password.*
import retrofit2.Call
import retrofit2.Response

class FindPasswordActivity : AppCompatActivity() {
    private val TAG = this.javaClass.toString()
    private lateinit var validationCheck : ValidationCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)

        init()

        initListener()
    }


    private fun init(){
        validationCheck = ValidationCheck()
    }

    private fun initListener() {
        // 백 버튼
        fp_img_back.setOnClickListener {
            finish()
        }

        // 이메일 전송 버튼
        fp_btn_send.setOnClickListener {
            val email = fp_edt_email.text.toString()
            if(validationCheck.checkEmailRegex(email)){
                val emailRequest = EmailRequest(email)
                val findPasswordService = RetrofitClient.kindItAccountService()
                val findPasswordCall = findPasswordService.findPassword(emailRequest)
                findPasswordCall.enqueue(object : retrofit2.Callback<CommonResponse>{
                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        Log.e(TAG, t.message.toString())
                    }

                    override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                        val responseBody = response.body()
                        if (responseBody?.result == "success") {
                            Log.i(TAG, responseBody.toString())
                            fp_edtl_email.helperText = ""
                            fp_tv_info.visibility = View.VISIBLE
                            fp_btn_send.text = "재발송하기"
                        }
                    }
                })

            }else{
                fp_edtl_email.helperText = "유효하지 않은 이메일 형식입니다."
                fp_tv_info.visibility = View.INVISIBLE
                fp_btn_send.text = "메일 발송"
            }
        }
    }
}