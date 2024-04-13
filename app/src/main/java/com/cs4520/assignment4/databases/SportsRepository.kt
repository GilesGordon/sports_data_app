package com.cs4520.assignment4.databases

import com.cs4520.assignment4.Game

class SportsRepository(private val sportsApi: SportsApi, private val sportsDao: SportsDao) {
    suspend fun getGames(league: Int? = null): List<Game> {
        return try {
            println("MyApp: before API")
            val gamesResponse  = sportsApi.getGames(league, "2022-2023", "6c904debb1cf6e6c2c16fc685ef3a3f2")
            val games = gamesResponse.response
            println("MyApp: games:" + games)

            val gameEntities = games.map { game ->
                GameEntity(game.id, game.date, game.time, game.timestamp, game.timezone, game.stage, game.week, game.status,
                    game.league, game.country, game.teams, game.scores)
            }
            println("MyApp: games entities:" + gameEntities)
            sportsDao.insertGames(gameEntities)

            println("MyApp: got to the spot")
            games
        } catch (e: Exception) {
            println("MyApp: Error inserting games into the database: ${e.message}")
            e.printStackTrace()
            val cachedProducts = sportsDao.getAllGames()
            cachedProducts.map { gameEntity ->
                Game(gameEntity.id, gameEntity.date, gameEntity.time, gameEntity.timestamp,
                    gameEntity.timezone, gameEntity.stage, gameEntity.week, gameEntity.status,
                    gameEntity.league, gameEntity.country, gameEntity.teams, gameEntity.scores)
            }
        }
    }
}