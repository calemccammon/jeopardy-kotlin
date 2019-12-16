package com.mccammon.jeopardykotlin.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory{
    private val client = OkHttpClient().newBuilder().build()
    val J_SERVICE_API :JServiceApi = retrofit().create(JServiceApi::class.java)

    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://jservice.io/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}
