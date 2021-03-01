package com.tribo_mkt.evaluation.data.api

import com.tribo_mkt.evaluation.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: ApiService =  initRetrofit().create(ApiService::class.java)
}