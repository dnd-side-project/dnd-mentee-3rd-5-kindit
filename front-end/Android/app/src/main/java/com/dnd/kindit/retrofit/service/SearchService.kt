package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.SearchItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService{
    @GET("api/menu/")
    fun getMenuList(
        @Header("Authorization") token: String
    ): Call<SearchItemsResponse>

    @GET("api/menu/search/")
    fun getMenuListBySearch(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String
    ): Call<SearchItemsResponse>

    
}