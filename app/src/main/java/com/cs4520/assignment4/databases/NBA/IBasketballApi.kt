package com.cs4520.assignment4.databases.NBA

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IBasketballApi {
    @GET(BasketballApi.ENDPOINT)
    suspend fun getGames(
        @Query("league") league: Number? = null,
        @Query("season") season: String? = null,
        @Header("x-rapidapi-key") apiKey: String

    ): BasketballGamesResponse
}