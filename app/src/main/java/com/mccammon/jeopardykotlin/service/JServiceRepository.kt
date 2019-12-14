package com.mccammon.jeopardykotlin.service

class JServiceRepository(private val api : JService) : BaseRepository() {

    suspend fun getClue(): Clue? {
        return safeApiCall(
            call = { api.getClueAsync().await()},
            errorMessage = "Error fetching clue"
        )?.get(0)
    }

}