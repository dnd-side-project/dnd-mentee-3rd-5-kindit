package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.FavoriteItemResponse
import com.dnd.kindit.retrofit.domain.response.SearchItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CommonService{
    @GET("api/menu/keyword/")
    fun getTagList(
        @Header("Authorization") token: String
    ): Call<FavoriteItemResponse>

    @POST("api/menu/keyword/")
    fun insertTagList(
        @Header("Authorization") token: String,
        @Query("preference_keyword") tags: List<Int>
    ): Call<CommonResponse>
}