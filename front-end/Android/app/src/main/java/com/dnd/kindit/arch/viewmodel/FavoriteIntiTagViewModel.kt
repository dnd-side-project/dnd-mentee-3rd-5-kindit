package com.dnd.kindit.arch.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd.kindit.arch.model.FavoriteTag
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.FavoriteItemResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response

class FavoriteIntiTagViewModel : ViewModel() {
    private val TAG = this.javaClass.toString()

    private val _favoriteTags = MutableLiveData<FavoriteItemResponse>()
    private val _successInsertTags = MutableLiveData<Boolean>()

    val favoriteTags: LiveData<FavoriteItemResponse>
        get() = _favoriteTags

    val successInsertTags: LiveData<Boolean>
        get() = _successInsertTags

    // Tags 가져오기
    fun getTags(context: Context){
        val token =  PreferenceManager.getString(context, "kindit_token").toString()

        val tagService = RetrofitClient.kindItCommonService()
        val tagCall = tagService.getTagList("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTU5OTU4MzE4NCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5ODk3ODM4NH0.xNyzxIONa1Z5DbTaKMWPqCU3IufSjE3QlLmKtOAdaIA")

        tagCall.enqueue(object : retrofit2.Callback<FavoriteItemResponse>{
            override fun onFailure(call: Call<FavoriteItemResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<FavoriteItemResponse>, response: Response<FavoriteItemResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _favoriteTags.value = responseBody
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                }
            }
        })
    }

    // 사용자 기반 태그 넣기
    fun insertTags(context: Context, list: ArrayList<FavoriteTag>){
        val tags = ArrayList<Int>()

        for(fav in list){
            if(fav.isSelected)
                tags.add(fav.id)
        }

        val token =  PreferenceManager.getString(context, "kindit_token").toString()

        val tagService = RetrofitClient.kindItCommonService()
        val tagCall = tagService.insertTagList("jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1LCJ1c2VybmFtZSI6Im1zbm8yQG5hdmVyLmNvbSIsImV4cCI6MTU5OTU4MzE4NCwiZW1haWwiOiJtc25vMkBuYXZlci5jb20iLCJvcmlnX2lhdCI6MTU5ODk3ODM4NH0.xNyzxIONa1Z5DbTaKMWPqCU3IufSjE3QlLmKtOAdaIA",
            tags)

        tagCall.enqueue(object: retrofit2.Callback<CommonResponse>{
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _successInsertTags.value = false
            }

            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        _successInsertTags.value = true
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), Void::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                    _successInsertTags.value = false
                }
            }

        })
    }
}