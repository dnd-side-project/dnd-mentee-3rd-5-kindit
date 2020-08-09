package com.dnd.kindit.retrofit

import com.dnd.kindit.retrofit.service.AccountService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{
    companion object{
        private lateinit var retrofit: Retrofit

        fun kindItAccountService(): AccountService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl("http://203.241.228.109:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(AccountService::class.java)
        }
    }
}