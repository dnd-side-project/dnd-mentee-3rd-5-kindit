package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.request.UserSignUpRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService{
    @GET("api/accounts/check-nickname/")
    fun checkUsableUserNickname(
        @Query("nickname") nickname: String
    ): Call<CommonResponse>

    @POST("api/accounts/registration/")
    fun signUp(
        @Body userSignUpRequest: UserSignUpRequest
    ): Call<CommonResponse>
}