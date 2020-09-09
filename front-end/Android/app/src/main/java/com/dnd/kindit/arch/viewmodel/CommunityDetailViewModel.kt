package com.dnd.kindit.arch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.CommunityDetailItemResponse
import com.dnd.kindit.retrofit.domain.response.CustomDetailResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response

class CommunityDetailViewModel : ViewModel() {
    private val TAG = this.javaClass.toString()

    private val _detailCommunity = MutableLiveData<CommunityDetailItemResponse>()

    val detailCommunity: LiveData<CommunityDetailItemResponse>
        get() = _detailCommunity

    fun getCommunityDetail(context: Context, id: Int){
        val token = PreferenceManager.getString(context, "kindit_token").toString()
        
        val communityService = RetrofitClient.kindItCommunityService()
        val communityCall = communityService.getCommunity("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTYwMDI2MjAxNCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5OTY1NzIxNH0.huecbUN9PLspgQ9p1oGBzqWXbTzPU972ftdVKSl_EEs", id)
        
        communityCall.enqueue(object : retrofit2.Callback<CommunityDetailItemResponse>{
            override fun onFailure(call: Call<CommunityDetailItemResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<CommunityDetailItemResponse>, response: Response<CommunityDetailItemResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _detailCommunity.value = responseBody
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                    // 실패 없음
                }
            }
        })
    }
}