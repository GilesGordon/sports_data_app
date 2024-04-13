package com.cs4520.assignment4.databases

import com.cs4520.assignment4.Game
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SportsApi {
    @GET(Api.ENDPOINT)
    suspend fun getGames(
        @Query("league") league: Number? = null,
        @Query("season") season: String? = null,
        @Header("x-rapidapi-key") apiKey: String

    ): GamesResponse
}