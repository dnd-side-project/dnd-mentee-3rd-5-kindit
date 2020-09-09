package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.CommunityDetailItemResponse
import com.dnd.kindit.retrofit.domain.response.CommunityItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommunityService{
    @GET("api/community/")
    fun getCommunityList(
        @Header("Authorization") token: String
    ): Call<CommunityItemsResponse>

    @GET("api/community/{id}")
    fun getCommunity(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ):Call<CommunityDetailItemResponse>
}