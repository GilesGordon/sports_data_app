package com.cs4520.assignment4.databases.NFL


data class FootballGamesResponse(
    val get: String,
    val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val response: List<FootballGame>
)
