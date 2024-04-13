package com.cs4520.assignment4.databases.MLS

data class SoccerFixture(
    val fixture: Fixture,
    val league: SoccerLeague,
    val teams: SoccerTeams,
    val goals: SoccerGoals,
    val score: SoccerScore,
)

data class Fixture(
    val id: Int,
    val referee: String?,
    val timezone: String,
    val date: String,
    val timestamp: Int,
    val periods: SoccerPeriods,
    val venue: SoccerVenue,
    val status: SoccerStatus,
)

data class SoccerPeriods(
    val first: Int?,
    val second: Int?,
)

data class SoccerVenue(
    val id: Int,
    val name: String,
    val city: String,
)

data class SoccerStatus(
    val long: String,
    val short: String,
    val elapsed: Int?,
)

data class SoccerLeague(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int,
    val round: String,
)

data class SoccerTeams(
    val home: SoccerTeam,
    val away: SoccerTeam,
)

data class SoccerTeam(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?,
)

data class SoccerGoals(
    val home: Int?,
    val away: Int?,
)

data class SoccerScore(
    val halftime: SoccerScoreTime,
    val fulltime: SoccerScoreTime,
    val extratime: SoccerScoreTime,
    val penalty: SoccerScoreTime,
)

data class SoccerScoreTime(
    val home: Int?,
    val away: Int?
)