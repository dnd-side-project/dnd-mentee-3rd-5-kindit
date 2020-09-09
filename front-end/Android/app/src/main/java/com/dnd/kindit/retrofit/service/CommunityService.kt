package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.CommunityItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CommunityService{
    @GET("api/community/")
    fun getCommunityList(
        @Header("Authorization") token: String
    ): Call<CommunityItemsResponse>

}