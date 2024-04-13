package com.cs4520.assignment4.databases

import androidx.room.TypeConverter
import com.cs4520.assignment4.Country
import com.cs4520.assignment4.League
import com.cs4520.assignment4.Score
import com.cs4520.assignment4.Scores
import com.cs4520.assignment4.Status
import com.cs4520.assignment4.Team
import com.cs4520.assignment4.Teams

class StatusConverter {
    @TypeConverter
    fun fromStatus(status: Status?): String? {
        return status?.let {
            "${it.long},${it.short},${it.timer}"
        }
    }

    @TypeConverter
    fun toStatus(statusString: String?): Status? {
        return statusString?.let {
            val parts = it.split(",")
            Status(parts[0], parts[1], parts.getOrNull(2))
        }
    }
}

class LeagueConverter {
    @TypeConverter
    fun fromLeague(league: League?): String? {
        return league?.let {
            "${it.id},${it.name},${it.type},${it.season},${it.logo}"
        }
    }

    @TypeConverter
    fun toLeague(leagueString: String?): League? {
        return leagueString?.let {
            val parts = it.split(",")
            League(parts[0].toInt(), parts[1], parts[2], parts[3], parts[4])
        }
    }
}

class CountryConverter {
    @TypeConverter
    fun fromCountry(country: Country?): String? {
        return country?.let {
            "${it.id},${it.name},${it.code},${it.flag}"
        }
    }

    @TypeConverter
    fun toCountry(countryString: String?): Country? {
        return countryString?.let {
            val parts = it.split(",")
            Country(parts[0].toInt(), parts[1], parts[2], parts[3])
        }
    }
}

class TeamsConverter {
    @TypeConverter
    fun fromTeams(teams: Teams?): String? {
        return teams?.let {
            "${it.home.id},${it.home.name},${it.home.logo}|${it.away.id},${it.away.name},${it.away.logo}"
        }
    }

    @TypeConverter
    fun toTeams(teamsString: String?): Teams? {
        return teamsString?.let {
            val teamParts = it.split("|")
            val homeParts = teamParts[0].split(",")
            val awayParts = teamParts[1].split(",")
            val home = Team(homeParts[0].toInt(), homeParts[1], homeParts[2])
            val away = Team(awayParts[0].toInt(), awayParts[1], awayParts[2])
            Teams(home, away)
        }
    }
}

class ScoresConverter {
    @TypeConverter
    fun fromScores(scores: Scores?): String? {
        return scores?.let {
            "${it.home.quarter_1},${it.home.quarter_2},${it.home.quarter_3},${it.home.quarter_4},${it.home.over_time},${it.home.total}|" +
                    "${it.away.quarter_1},${it.away.quarter_2},${it.away.quarter_3},${it.away.quarter_4},${it.away.over_time},${it.away.total}"
        }
    }

    @TypeConverter
    fun toScores(scoresString: String?): Scores? {
        return scoresString?.let {
            val scoreParts = it.split("|")
            val homeParts = scoreParts[0].split(",")
            val awayParts = scoreParts[1].split(",")
            val home = Score(homeParts[0].toInt(), homeParts[1].toInt(), homeParts[2].toInt(), homeParts[3].toInt(),
                homeParts[4].toIntOrNull(), homeParts[5].toInt())
            val away = Score(awayParts[0].toInt(), awayParts[1].toInt(), awayParts[2].toInt(), awayParts[3].toInt(),
                awayParts[4].toIntOrNull(), awayParts[5].toInt())
            Scores(home, away)
        }
    }
}