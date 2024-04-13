package com.cs4520.assignment4.databases.MLS

import androidx.room.TypeConverter

class FixtureConverter {
    @TypeConverter
    fun fromFixture(fixture: Fixture?): String? {
        return fixture?.let {
            "${it.id},${it.referee},${it.timezone},${it.date},${it.timestamp},${it.periods.first},${it.periods.second},${it.venue.id},${it.venue.name},${it.venue.city},${it.status.long},${it.status.short},${it.status.elapsed}"
        }
    }

    @TypeConverter
    fun toFixture(fixtureString: String?): Fixture? {
        return fixtureString?.let {
            val parts = it.split(",")
            Fixture(
                parts[0].toInt(),
                parts[1].takeIf { it != "null" },
                parts[2],
                parts[3],
                parts[4].toInt(),
                SoccerPeriods(parts[5].toIntOrNull(), parts[6].toIntOrNull()),
                SoccerVenue(parts[7].toInt(), parts[8], parts[9]),
                SoccerStatus(parts[10], parts[11], parts[12].toIntOrNull())
            )
        }
    }
}

class LeagueConverter {
    @TypeConverter
    fun fromLeague(league: SoccerLeague?): String? {
        return league?.let {
            "${it.id},${it.name},${it.country},${it.logo},${it.flag},${it.season},${it.round}"
        }
    }

    @TypeConverter
    fun toLeague(leagueString: String?): SoccerLeague? {
        return leagueString?.let {
            val parts = it.split(",")
            SoccerLeague(
                parts[0].toInt(),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5].toInt(),
                parts[6]
            )
        }
    }
}

class TeamsConverter {
    @TypeConverter
    fun fromTeams(teams: SoccerTeams?): String? {
        return teams?.let {
            "${it.home.id},${it.home.name},${it.home.logo},${it.home.winner},${it.away.id},${it.away.name},${it.away.logo},${it.away.winner}"
        }
    }

    @TypeConverter
    fun toTeams(teamsString: String?): SoccerTeams? {
        return teamsString?.let {
            val parts = it.split(",")
            val home = SoccerTeam(parts[0].toInt(), parts[1], parts[2], parts[3].toBooleanStrictOrNull())
            val away = SoccerTeam(parts[4].toInt(), parts[5], parts[6], parts[7].toBooleanStrictOrNull())
            SoccerTeams(home, away)
        }
    }
}

class GoalsConverter {
    @TypeConverter
    fun fromGoals(goals: SoccerGoals?): String? {
        return goals?.let {
            "${it.home},${it.away}"
        }
    }

    @TypeConverter
    fun toGoals(goalsString: String?): SoccerGoals? {
        return goalsString?.let {
            val parts = it.split(",")
            SoccerGoals(parts[0].toIntOrNull(), parts[1].toIntOrNull())
        }
    }
}

class ScoreConverter {
    @TypeConverter
    fun fromScore(score: SoccerScore?): String? {
        return score?.let {
            "${it.halftime.home},${it.halftime.away},${it.fulltime.home},${it.fulltime.away},${it.extratime.home},${it.extratime.away},${it.penalty.home},${it.penalty.away}"
        }
    }

    @TypeConverter
    fun toScore(scoreString: String?): SoccerScore? {
        return scoreString?.let {
            val parts = it.split(",")
            SoccerScore(
                SoccerScoreTime(parts[0].toIntOrNull(), parts[1].toIntOrNull()),
                SoccerScoreTime(parts[2].toIntOrNull(), parts[3].toIntOrNull()),
                SoccerScoreTime(parts[4].toIntOrNull(), parts[5].toIntOrNull()),
                SoccerScoreTime(parts[6].toIntOrNull(), parts[7].toIntOrNull())
            )
        }
    }
}