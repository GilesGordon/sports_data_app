package com.cs4520.assignment4.databases.NBA

import androidx.room.TypeConverter

class NBAStatusConverter {
    @TypeConverter
    fun fromStatus(status: NBAStatus?): String? {
        return status?.let {
            "${it.long},${it.short},${it.timer}"
        }
    }

    @TypeConverter
    fun toStatus(statusString: String?): NBAStatus? {
        return statusString?.let {
            val parts = it.split(",")
            NBAStatus(parts[0], parts[1], parts.getOrNull(2))
        }
    }
}

class NBALeagueConverter {
    @TypeConverter
    fun fromLeague(league: NBALeague?): String? {
        return league?.let {
            "${it.id},${it.name},${it.type},${it.season},${it.logo}"
        }
    }

    @TypeConverter
    fun toLeague(leagueString: String?): NBALeague? {
        return leagueString?.let {
            val parts = it.split(",")
            NBALeague(parts[0].toInt(), parts[1], parts[2], parts[3], parts[4])
        }
    }
}

class NBACountryConverter {
    @TypeConverter
    fun fromCountry(country: NBACountry?): String? {
        return country?.let {
            "${it.id},${it.name},${it.code},${it.flag}"
        }
    }

    @TypeConverter
    fun toCountry(countryString: String?): NBACountry? {
        return countryString?.let {
            val parts = it.split(",")
            NBACountry(parts[0].toInt(), parts[1], parts[2], parts[3])
        }
    }
}

class NBATeamsConverter {
    @TypeConverter
    fun fromTeams(teams: NBATeams?): String? {
        return teams?.let {
            "${it.home.id},${it.home.name},${it.home.logo}|${it.away.id},${it.away.name},${it.away.logo}"
        }
    }

    @TypeConverter
    fun toTeams(teamsString: String?): NBATeams? {
        return teamsString?.let {
            val teamParts = it.split("|")
            val homeParts = teamParts[0].split(",")
            val awayParts = teamParts[1].split(",")
            val home = NBATeam(homeParts[0].toInt(), homeParts[1], homeParts[2])
            val away = NBATeam(awayParts[0].toInt(), awayParts[1], awayParts[2])
            NBATeams(home, away)
        }
    }
}

class NBAScoresConverter {
    @TypeConverter
    fun fromScores(scores: NBAScores?): String? {
        return scores?.let {
            "${it.home.quarter_1},${it.home.quarter_2},${it.home.quarter_3},${it.home.quarter_4},${it.home.over_time},${it.home.total}|" +
                    "${it.away.quarter_1},${it.away.quarter_2},${it.away.quarter_3},${it.away.quarter_4},${it.away.over_time},${it.away.total}"
        }
    }

    @TypeConverter
    fun toScores(scoresString: String?): NBAScores? {
        return scoresString?.let {
            val scoreParts = it.split("|")
            val homeParts = scoreParts[0].split(",")
            val awayParts = scoreParts[1].split(",")
            val home = NBAScore(homeParts[0].toInt(), homeParts[1].toInt(), homeParts[2].toInt(), homeParts[3].toInt(),
                homeParts[4].toIntOrNull(), homeParts[5].toInt())
            val away = NBAScore(awayParts[0].toInt(), awayParts[1].toInt(), awayParts[2].toInt(), awayParts[3].toInt(),
                awayParts[4].toIntOrNull(), awayParts[5].toInt())
            NBAScores(home, away)
        }
    }
}