package com.cs4520.assignment4.databases

import com.cs4520.assignment4.databases.MLS.ISoccerApi
import com.cs4520.assignment4.databases.MLS.SoccerDao
import com.cs4520.assignment4.databases.MLS.SoccerFixture
import com.cs4520.assignment4.databases.MLS.SoccerFixtureEntity
import com.cs4520.assignment4.databases.NBA.BasketballGame
import com.cs4520.assignment4.databases.NBA.BasketballGameEntity
import com.cs4520.assignment4.databases.NBA.IBasketballApi
import com.cs4520.assignment4.databases.NBA.BasketballDao
import com.cs4520.assignment4.databases.NFL.FootballDao
import com.cs4520.assignment4.databases.NFL.FootballGame
import com.cs4520.assignment4.databases.NFL.FootballGameEntity
import com.cs4520.assignment4.databases.NFL.IFootballApi

class SportsRepository(private val basketballApi: IBasketballApi,
                       private val basketballDao: BasketballDao,
                       private val soccerApi: ISoccerApi,
                       private val soccerDao: SoccerDao,
                       private val footballApi: IFootballApi,
                       private val footballDao: FootballDao,
    ) {
    suspend fun getBasketballGames(): List<BasketballGame> {
        return try {
            println("MyApp: before API")
            val gamesResponse  = basketballApi.getGames(12, "2022-2023", "6c904debb1cf6e6c2c16fc685ef3a3f2")
            val games = gamesResponse.response
            println("MyApp: games:" + games)

            val gameEntities = games.map { game ->
                BasketballGameEntity(game.id, game.date, game.time, game.timestamp, game.timezone, game.stage, game.week, game.status,
                    game.league, game.country, game.teams, game.scores)
            }
            println("MyApp: games entities:" + gameEntities)
            basketballDao.insertGames(gameEntities)

            println("MyApp: got to the spot")
            games
        } catch (e: Exception) {
            println("MyApp: Error inserting games into the database: ${e.message}")
            e.printStackTrace()
            val cachedProducts = basketballDao.getAllGames()
            cachedProducts.map { gameEntity ->
                BasketballGame(gameEntity.id, gameEntity.date, gameEntity.time, gameEntity.timestamp,
                    gameEntity.timezone, gameEntity.stage, gameEntity.week, gameEntity.status,
                    gameEntity.league, gameEntity.country, gameEntity.teams, gameEntity.scores)
            }
        }
    }

    suspend fun getSoccerFixtures(): List<SoccerFixture> {
        return try {
            println("MyApp: before API")
            val fixturesResponse  = soccerApi.getGames(909, "2024", "6c904debb1cf6e6c2c16fc685ef3a3f2")
            val fixtures = fixturesResponse.response
            println("MyApp: fixtures:" + fixtures)

            val gameEntities = fixtures.map { fixture ->
                SoccerFixtureEntity(fixture.fixture, fixture.league, fixture.teams, fixture.goals,
                    fixture.score)
            }
            println("MyApp: soccer fixture entities:" + gameEntities)
            soccerDao.insertFixtures(gameEntities)

            println("MyApp: got to the spot")
            fixtures
        } catch (e: Exception) {
            println("MyApp: Error inserting fixtures into the database: ${e.message}")
            e.printStackTrace()
            val cachedProducts = soccerDao.getAllFixtures()
            cachedProducts.map { fixtureEntity ->
                SoccerFixture(fixtureEntity.fixture, fixtureEntity.league, fixtureEntity.teams,
                    fixtureEntity.goals, fixtureEntity.score)
            }
        }
    }

    suspend fun getFootballGames(): List<FootballGame> {
        return try {
            println("MyApp: before API")
            val gamesResponse  = footballApi.getGames(1, 2023,
                "6c904debb1cf6e6c2c16fc685ef3a3f2")
            val games = gamesResponse.response
            println("MyApp: games:" + games)

            val gameEntities = games.map { game ->
                FootballGameEntity(game.game, game.league, game.teams, game.scores)
            }
            println("MyApp: games entities:" + gameEntities)
            footballDao.insertGames(gameEntities)

            println("MyApp: got to the spot")
            games
        } catch (e: Exception) {
            println("MyApp: Error inserting games into the database: ${e.message}")
            e.printStackTrace()
            val cachedProducts = footballDao.getAllGames()
            cachedProducts.map { gameEntity ->
                FootballGame(gameEntity.game, gameEntity.league, gameEntity.teams,
                    gameEntity.scores)
            }
        }
    }
}