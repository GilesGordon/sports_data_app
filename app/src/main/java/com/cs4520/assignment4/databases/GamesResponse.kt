package com.cs4520.assignment4.databases

import com.cs4520.assignment4.Game

data class GamesResponse(
    val get: String,
    val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val response: List<Game>
)
