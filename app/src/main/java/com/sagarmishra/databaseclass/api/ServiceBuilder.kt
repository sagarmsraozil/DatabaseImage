package com.sagarmishra.databaseclass.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:90/"
    var token :String? = null
    var offline:Boolean = true
    val okHttp = OkHttpClient.Builder()

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = retrofitBuilder.build()

    fun <T> buildService(service:Class<T>):T
    {
        return retrofit.create(service)
    }
}