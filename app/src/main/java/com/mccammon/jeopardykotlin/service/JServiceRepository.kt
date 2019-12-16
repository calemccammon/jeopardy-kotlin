package com.mccammon.jeopardykotlin.service

class JServiceRepository(private val api : JServiceApi) : BaseRepository() {

    suspend fun getClue() : Clue? {
        val clueResponse = safeApiCall(
            call = {api.getClue().await()},
            errorMessage = "Error Fetching Popular Movies"
        )
        return clueResponse?.get(0)
    }

}