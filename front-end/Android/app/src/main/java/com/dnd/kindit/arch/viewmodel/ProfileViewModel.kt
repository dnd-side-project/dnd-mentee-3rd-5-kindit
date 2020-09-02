package com.dnd.kindit.arch.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.request.ProfileUpdateRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.UserResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val TAG = this.javaClass.toString()

    private val _user = MutableLiveData<UserResponse>()
    private val _check = MutableLiveData<Boolean>()

    val user: LiveData<UserResponse>
        get() = _user
    val check: LiveData<Boolean>
        get() = _check


    // 사용자 프로필 정보 가져오기
    fun getProfile(context: Context) {
        val token = PreferenceManager.getString(context, "kindit_token").toString()

        val profileService = RetrofitClient.kindItAccountService()
        val profileCall =
            profileService.getUserProfile("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTU5OTU4MzE4NCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5ODk3ODM4NH0.xNyzxIONa1Z5DbTaKMWPqCU3IufSjE3QlLmKtOAdaIA")

        profileCall.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _user.value = responseBody
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                    // error 처리 => 2020.09.02 기준 서버에서 주는 errorbody 없음
                }
            }
        })
    }

    fun modifyProfile(context: Context, nickname: String) {
        val token = PreferenceManager.getString(context, "kindit_token").toString()
        val profileService = RetrofitClient.kindItAccountService()
        val profileCall = profileService.modifyUserProfile(
            "jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTU5OTU4MzE4NCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5ODk3ODM4NH0.xNyzxIONa1Z5DbTaKMWPqCU3IufSjE3QlLmKtOAdaIA",
            ProfileUpdateRequest(nickname)
        )

        profileCall.enqueue(object : retrofit2.Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _check.value = true
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                    // error 처리 => 2020.09.02 기준 서버에서 주는 errorbody 없음
                }
            }
        })
    }
}