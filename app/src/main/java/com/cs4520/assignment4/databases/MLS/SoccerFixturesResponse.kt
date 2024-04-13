package com.cs4520.assignment4.databases.MLS

import com.cs4520.assignment4.databases.NBA.BasketballGame

data class SoccerFixturesResponse(
    val get: String,
    val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val response: List<SoccerFixture>
)