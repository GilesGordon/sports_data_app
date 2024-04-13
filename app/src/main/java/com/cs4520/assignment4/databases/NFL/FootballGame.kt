package com.cs4520.assignment4.databases.NFL

data class FootballGame(
    val game: Game,
    val league: League,
    val teams: Teams,
    val scores: Scores,
)

data class Game(
    val id: Int,
    val stage: String,
    val week: String,
    val date: Date,
    val venue: Venue,
    val status: Status,
)

data class Date(
    val timezone: String,
    val date: String,
    val time: String,
    val timestamp: Int,
)
data class Venue(
    val name: String,
    val city: String,
)
data class Status(
    val short: String,
    val long: String,
    val timer: String?,
)

data class League(
    val id: Int,
    val name: String,
    val season: String,
    val logo: String,
    val country: Country,
)

data class Country(
    val name: String,
    val code: String,
    val flag: String,
)

data class Teams(
    val home: Team,
    val away: Team,
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
)

data class Scores(
    val home: TeamScore,
    val away: TeamScore,
)

data class TeamScore(
    val quarter_1: Int?,
    val quarter_2: Int?,
    val quarter_3: Int?,
    val quarter_4: Int?,
    val overtime: Int?,
    val total: Int?,
)