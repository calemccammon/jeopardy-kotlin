package com.mccammon.jeopardykotlin.service

class JServiceRepository(private val api : JServiceApi) : BaseRepository() {

    suspend fun getClue() : Clue? {
        val clueResponse = safeApiCall(
            call = {api.getClue().await()},
            errorMessage = "Error Fetching Clue"
        )
        if(clueResponse?.get(0)!!.answer.isEmpty() || clueResponse?.get(0)!!.question.isEmpty()) {
            return getClue()
        }
        return clueResponse?.get(0)
    }
}