package com.cs4520.assignment4.databases.NFL

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IFootballApi {
    @GET(FootballApi.ENDPOINT)
    suspend fun getGames(
        @Query("league") league: Int? = null,
        @Query("season") season: Int? = null,
        @Header("x-rapidapi-key") apiKey: String

    ): FootballGamesResponse
}