package com.cs4520.assignment4.databases.NFL

import androidx.room.TypeConverter

class GameConverter {
    @TypeConverter
    fun fromGame(game: Game?): String? {
        return game?.let {
            "${it.id},${it.stage},${it.week},${it.date.timezone},${it.date.date},${it.date.time},${it.date.timestamp},${it.venue.name},${it.venue.city},${it.status.short},${it.status.long},${it.status.timer}"
        }
    }

    @TypeConverter
    fun toGame(gameString: String?): Game? {
        return gameString?.let {
            val parts = it.split(",")
            Game(
                parts[0].toInt(),
                parts[1],
                parts[2],
                Date(parts[3], parts[4], parts[5], parts[6].toInt()),
                Venue(parts[7], parts[8]),
                Status(parts[9], parts[10], parts[11].takeIf { it != "null" })
            )
        }
    }
}

class LeagueConverter {
    @TypeConverter
    fun fromLeague(league: League?): String? {
        return league?.let {
            "${it.id},${it.name},${it.season},${it.logo},${it.country.name},${it.country.code},${it.country.flag}"
        }
    }

    @TypeConverter
    fun toLeague(leagueString: String?): League? {
        return leagueString?.let {
            val parts = it.split(",")
            League(
                parts[0].toInt(),
                parts[1],
                parts[2],
                parts[3],
                Country(parts[4], parts[5], parts[6])
            )
        }
    }
}

class TeamsConverter {
    @TypeConverter
    fun fromTeams(teams: Teams?): String? {
        return teams?.let {
            "${it.home.id},${it.home.name},${it.home.logo},${it.away.id},${it.away.name},${it.away.logo}"
        }
    }

    @TypeConverter
    fun toTeams(teamsString: String?): Teams? {
        return teamsString?.let {
            val parts = it.split(",")
            Teams(
                Team(parts[0].toInt(), parts[1], parts[2]),
                Team(parts[3].toInt(), parts[4], parts[5])
            )
        }
    }
}

class ScoresConverter {
    @TypeConverter
    fun fromScores(scores: Scores?): String? {
        return scores?.let {
            "${it.home.quarter_1},${it.home.quarter_2},${it.home.quarter_3},${it.home.quarter_4},${it.home.overtime},${it.home.total},${it.away.quarter_1},${it.away.quarter_2},${it.away.quarter_3},${it.away.quarter_4},${it.away.overtime},${it.away.total}"
        }
    }

    @TypeConverter
    fun toScores(scoresString: String?): Scores? {
        return scoresString?.let {
            val parts = it.split(",")
            Scores(
                TeamScore(parts[0].toIntOrNull(), parts[1].toIntOrNull(), parts[2].toIntOrNull(), parts[3].toIntOrNull(), parts[4].toIntOrNull(), parts[5].toIntOrNull()),
                TeamScore(parts[6].toIntOrNull(), parts[7].toIntOrNull(), parts[8].toIntOrNull(), parts[9].toIntOrNull(), parts[10].toIntOrNull(), parts[11].toIntOrNull())
            )
        }
    }
}