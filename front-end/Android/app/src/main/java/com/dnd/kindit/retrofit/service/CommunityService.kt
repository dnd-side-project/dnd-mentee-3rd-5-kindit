package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.request.CommunityWriteRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.CommunityDetailItemResponse
import com.dnd.kindit.retrofit.domain.response.CommunityItemsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

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

    @Multipart
    @POST("api/community/")
    fun uploadPost1(
        @Header("Authorization") token: String,
        @Part("content") content : RequestBody,
        @Part upload_image: MultipartBody.Part
    ): Call<CommonResponse>

    @Multipart
    @POST("api/community/")
    fun uploadPost2(
        @Header("Authorization") token: String,
        @Part("content") content : RequestBody
    ): Call<CommonResponse>
}