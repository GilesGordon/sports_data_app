package com.cs4520.assignment4

data class Game(
    val id: Int,
    val date: String,
    val time: String,
    val timestamp: Int,
    val timezone: String,
    val stage: String?,
    val week: String?,
    val status: Status,
    val league: League,
    val country: Country,
    val teams: Teams,
    val scores: Scores,
)

data class Status(
    val long: String,
    val short: String,
    val timer: String?,
)

data class League(
    val id: Int,
    val name: String,
    val type: String,
    val season: String,
    val logo: String
)

data class Country(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String,
)

data class Teams(
    val home: Team,
    val away: Team,
)

data class Team(
    val id: Number,
    val name: String,
    val logo: String,
)

data class Scores(
    val home: Score,
    val away: Score,
)

data class Score(
    val quarter_1: Int,
    val quarter_2: Int,
    val quarter_3: Int,
    val quarter_4: Int,
    val over_time: Int?,
    val total: Int,
)