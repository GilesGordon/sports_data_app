package com.cs4520.assignment4.databases.NBA

data class BasketballGamesResponse(
    val get: String,
    val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val response: List<BasketballGame>
)
