package com.dnd.kindit.arch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.SearchItemsResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val TAG = this.javaClass.toString()

    private val _searchItemList = MutableLiveData<SearchItemsResponse>()

    val searchItemList: LiveData<SearchItemsResponse>
        get() = _searchItemList


    // 기본 검색 데이터 가져오기
    fun getMenuList(context: Context){
        val token = PreferenceManager.getString(context, "kindit_token").toString()

        val menusService = RetrofitClient.kindItSearchService()
        val menusCall = menusService.getMenuList("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTU5OTU4MzE4NCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5ODk3ODM4NH0.xNyzxIONa1Z5DbTaKMWPqCU3IufSjE3QlLmKtOAdaIA")
        menusCall.enqueue(object: retrofit2.Callback<SearchItemsResponse>{
            override fun onFailure(call: Call<SearchItemsResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<SearchItemsResponse>, response: Response<SearchItemsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _searchItemList.value = responseBody
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create().fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                }
            }

        })
    }

}