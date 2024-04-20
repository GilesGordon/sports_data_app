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


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SoccerDatabase::class.java).allowMainThreadQueries().build()
        soccerDao = db.soccerDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndReadInList() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        soccerDao.insertFixtures(listOf(soccerFixture))


        val byName = soccerDao.getAllFixtures()
        assertThat(byName[0], equalTo(soccerFixture))
    }
}