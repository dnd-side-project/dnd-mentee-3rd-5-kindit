package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.SearchItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface SearchService{
    @GET("api/menu/")
    fun getMenuList(
        @Header("Authorization") token: String
    ): Call<SearchItemsResponse>
}