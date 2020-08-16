package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.request.UserLoginRequest
import com.dnd.kindit.retrofit.domain.request.UserSignUpRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.UserLoginResponse
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

    @POST("api/accounts/login/")
    fun login(
        @Body userLoginRequest: UserLoginRequest
    ): Call<UserLoginResponse>
}