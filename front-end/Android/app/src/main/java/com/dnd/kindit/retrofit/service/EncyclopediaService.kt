package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.response.EncyclopediaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EncyclopediaService {

    @GET("api/menu")
    fun getMenuList(
        @Header("Authorization") token: String
    ) : Call<EncyclopediaResponse>

}