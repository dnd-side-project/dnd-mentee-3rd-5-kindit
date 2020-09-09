package com.dnd.kindit.arch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.CommunityItemsResponse
import com.dnd.kindit.retrofit.domain.response.SearchItemsResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response

class CommunityViewModel : ViewModel() {
    private val TAG = this.javaClass.toString()

    private val _communityItemList = MutableLiveData<CommunityItemsResponse>()

    val communityItemList: LiveData<CommunityItemsResponse>
        get() = _communityItemList


    // 커뮤니티 메인 데이터 가져오기
    fun getCommunityList(context: Context) {
        val token = PreferenceManager.getString(context, "kindit_token").toString()

        val communityService = RetrofitClient.kindItCommunityService()
        val communityCall = communityService.getCommunityList("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTYwMDI2MjAxNCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5OTY1NzIxNH0.huecbUN9PLspgQ9p1oGBzqWXbTzPU972ftdVKSl_EEs")

        communityCall.enqueue(object : retrofit2.Callback<CommunityItemsResponse>{
            override fun onFailure(call: Call<CommunityItemsResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<CommunityItemsResponse>, response: Response<CommunityItemsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _communityItemList.value = responseBody
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                }
            }

        })
    }
}