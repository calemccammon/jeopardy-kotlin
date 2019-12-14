package com.mccammon.jeopardykotlin.service

data class Clue(val id: Int, val answer: String, val question: String, val value: Int?,
    val airdate: String, val created_at: String, val updated_at: String, val category_id: Int,
    val game_id: Int?, val invalid_count: Int?) {

}