package com.dnd.kindit.retrofit.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountService{
    @GET("api/accounts/auth/check-nickname/")
    fun checkUsableUserNickname(
        @Query("nickname") nickname: String
    ): Call<String>
}