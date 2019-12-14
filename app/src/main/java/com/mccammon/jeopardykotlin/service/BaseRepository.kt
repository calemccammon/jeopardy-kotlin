package com.mccammon.jeopardykotlin.service

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : CallResult<T> = safeApiResult(call,errorMessage)
        var data : T? = null

        when(result) {
            is CallResult.Success ->
                data = result.data
            is CallResult.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }
        return data
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : CallResult<T> {
        val response = call.invoke()
        if(response.isSuccessful) {
            return CallResult.Success(response.body()!!)
        }
        return CallResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}