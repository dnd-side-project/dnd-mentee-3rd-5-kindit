package com.dnd.kindit.retrofit.service

import com.dnd.kindit.retrofit.domain.request.EmailRequest
import com.dnd.kindit.retrofit.domain.request.UserLoginRequest
import com.dnd.kindit.retrofit.domain.request.UserSignUpRequest
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.UserLoginResponse
import com.dnd.kindit.retrofit.domain.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

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

    @POST("api/accounts/password-reset/")
    fun findPassword(
        @Body emailRequest : EmailRequest
    ): Call<CommonResponse>

    @GET("api/accounts/user/")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<UserResponse>
}