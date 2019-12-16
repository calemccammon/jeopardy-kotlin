package com.mccammon.jeopardykotlin.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface JServiceApi {
    @GET("api/random")
    fun getClue(): Deferred<Response<List<Clue>>>
}