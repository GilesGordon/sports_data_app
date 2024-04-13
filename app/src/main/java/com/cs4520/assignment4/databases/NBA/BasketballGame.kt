package com.cs4520.assignment4.databases.NBA

data class BasketballGame(
    val id: Int,
    val date: String,
    val time: String,
    val timestamp: Int,
    val timezone: String,
    val stage: String?,
    val week: String?,
    val status: NBAStatus,
    val league: NBALeague,
    val country: NBACountry,
    val teams: NBATeams,
    val scores: NBAScores,
)

data class NBAStatus(
    val long: String,
    val short: String,
    val timer: String?,
)

data class NBALeague(
    val id: Int,
    val name: String,
    val type: String,
    val season: String,
    val logo: String
)

data class NBACountry(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String,
)

data class NBATeams(
    val home: NBATeam,
    val away: NBATeam,
)

data class NBATeam(
    val id: Number,
    val name: String,
    val logo: String,
)

data class NBAScores(
    val home: NBAScore,
    val away: NBAScore,
)

data class NBAScore(
    val quarter_1: Int,
    val quarter_2: Int,
    val quarter_3: Int,
    val quarter_4: Int,
    val over_time: Int?,
    val total: Int,
)