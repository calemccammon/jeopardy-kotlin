package com.mccammon.jeopardykotlin.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object ApiFactory {

    private val client = OkHttpClient().newBuilder().build()

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://jservice.io/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val jService : JService = retrofit().create(JService::class.java)
}

interface JService {

    @GET("api/random")
    fun getClueAsync(): Deferred<Response<List<Clue>>>

}