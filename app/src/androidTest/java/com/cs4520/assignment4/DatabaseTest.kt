package com.cs4520.assignment4

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.assignment4.databases.MLS.Fixture
import com.cs4520.assignment4.databases.MLS.SoccerDao
import com.cs4520.assignment4.databases.MLS.SoccerDatabase
import com.cs4520.assignment4.databases.MLS.SoccerFixture
import com.cs4520.assignment4.databases.MLS.SoccerFixtureEntity
import com.cs4520.assignment4.databases.MLS.SoccerGoals
import com.cs4520.assignment4.databases.MLS.SoccerLeague
import com.cs4520.assignment4.databases.MLS.SoccerPeriods
import com.cs4520.assignment4.databases.MLS.SoccerScore
import com.cs4520.assignment4.databases.MLS.SoccerScoreTime
import com.cs4520.assignment4.databases.MLS.SoccerStatus
import com.cs4520.assignment4.databases.MLS.SoccerTeam
import com.cs4520.assignment4.databases.MLS.SoccerTeams
import com.cs4520.assignment4.databases.MLS.SoccerVenue
import com.cs4520.assignment4.databases.NFL.Country
import com.cs4520.assignment4.databases.NFL.Date
import com.cs4520.assignment4.databases.NFL.FootballDao
import com.cs4520.assignment4.databases.NFL.FootballDatabase
import com.cs4520.assignment4.databases.NFL.FootballGame
import com.cs4520.assignment4.databases.NFL.FootballGameEntity
import com.cs4520.assignment4.databases.NFL.Game
import com.cs4520.assignment4.databases.NFL.League
import com.cs4520.assignment4.databases.NFL.Scores
import com.cs4520.assignment4.databases.NFL.Status
import com.cs4520.assignment4.databases.NFL.Team
import com.cs4520.assignment4.databases.NFL.TeamScore
import com.cs4520.assignment4.databases.NFL.Teams
import com.cs4520.assignment4.databases.NFL.Venue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var soccerDao: SoccerDao
    private lateinit var db: SoccerDatabase
    private val soccerFixture = SoccerFixtureEntity(
        fixture = Fixture(
            id = 1,
            referee = "Referee Name",
            timezone = "GMT",
            date = "2024-04-19",
            timestamp = 1618875455,
            periods = SoccerPeriods(
                first = 45,
                second = 45
            ),
            venue = SoccerVenue(
                id = 1,
                name = "Stadium",
                city = "City"
            ),
            status = SoccerStatus(
                long = "Match Finished",
                short = "FT",
                elapsed = 90
            )
        ),
        league = SoccerLeague(
            id = 1,
            name = "Premier League",
            country = "England",
            logo = "logo_url",
            flag = "flag_url",
            season = 2023,
            round = "Round 1"
        ),
        teams = SoccerTeams(
            home = SoccerTeam(
                id = 1,
                name = "Team A",
                logo = "logo_url",
                winner = true
            ),
            away = SoccerTeam(
                id = 2,
                name = "Team B",
                logo = "logo_url",
                winner = false
            )
        ),
        goals = SoccerGoals(
            home = 2,
            away = 1
        ),
        score = SoccerScore(
            halftime = SoccerScoreTime(
                home = 1,
                away = 1
            ),
            fulltime = SoccerScoreTime(
                home = 2,
                away = 1
            ),
            extratime = SoccerScoreTime(
                home = null,
                away = null
            ),
            penalty = SoccerScoreTime(
                home = null,
                away = null
            )
        )
    )

    private lateinit var footballDao: FootballDao
    private lateinit var footballdb: FootballDatabase
    private val footballGame = FootballGameEntity(
        game = Game(
            id = 1,
            stage = "Final",
            week = "Week 1",
            date = Date(
                timezone = "GMT",
                date = "2024-04-19",
                time = "22:37",
                timestamp = 1618875455
            ),
            venue = Venue(
                name = "Stadium",
                city = "City"
            ),
            status = Status(
                short = "HT",
                long = "Half Time",
                timer = null
            )
        ),
        league = League(
            id = 1,
            name = "Premier League",
            season = "2023-2024",
            logo = "logo_url",
            country = Country(
                name = "England",
                code = "EN",
                flag = "flag_url"
            )
        ),
        teams = Teams(
            home = Team(
                id = 1,
                name = "Team A",
                logo = "logo_url"
            ),
            away = Team(
                id = 2,
                name = "Team B",
                logo = "logo_url"
            )
        ),
        scores = Scores(
            home = TeamScore(
                quarter_1 = 1,
                quarter_2 = 2,
                quarter_3 = 3,
                quarter_4 = 4,
                overtime = null,
                total = 10
            ),
            away = TeamScore(
                quarter_1 = 1,
                quarter_2 = 2,
                quarter_3 = 3,
                quarter_4 = 4,
                overtime = null,
                total = 10
            )
        )
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SoccerDatabase::class.java).allowMainThreadQueries().build()
        soccerDao = db.soccerDao()

        footballdb = Room.inMemoryDatabaseBuilder(
            context, FootballDatabase::class.java).allowMainThreadQueries().build()
        footballDao = footballdb.footballDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadInList() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        soccerDao.insertFixtures(listOf(soccerFixture))


        val byName = soccerDao.getAllFixtures()
        assertThat(byName[0], equalTo(soccerFixture))
    }

    @Test
    fun insertAndGetFootballGame() = runBlocking {
        footballDao.insertGames(listOf(footballGame))

        val byName = footballDao.getAllGames()
        assertThat(byName[0], equalTo(footballGame))
    }
}