package com.dnd.kindit.retrofit

import com.dnd.kindit.retrofit.service.AccountService
import com.dnd.kindit.retrofit.service.CommonService
import com.dnd.kindit.retrofit.service.SearchService
import com.dnd.kindit.util.CommonUtils
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{
    companion object{
        private lateinit var retrofit: Retrofit

        fun kindItAccountService(): AccountService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(CommonUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(AccountService::class.java)
        }

        fun kindItSearchService(): SearchService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(CommonUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(SearchService::class.java)
        }

        fun kindItCommonService(): CommonService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(CommonUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(CommonService::class.java)
        }
    }
}