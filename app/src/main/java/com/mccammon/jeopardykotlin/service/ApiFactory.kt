package com.mccammon.jeopardykotlin.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
object ApiFactory {

    private val logger = HttpLoggingInterceptor()
    private val client = OkHttpClient().newBuilder()
    val J_SERVICE_API :JServiceApi = retrofit().create(JServiceApi::class.java)

    fun retrofit() : Retrofit  {
        logger.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logger)

        return Retrofit.Builder()
            .client(client.build())
            .baseUrl("http://jservice.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
