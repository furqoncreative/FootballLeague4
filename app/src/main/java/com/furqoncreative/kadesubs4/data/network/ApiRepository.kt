package com.furqoncreative.kadesubs4.data.network

import com.furqoncreative.kadesubs4.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiRepository {
    private fun iniRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.TSDB_API_KEY + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return iniRetrofit().create(service)
    }
}