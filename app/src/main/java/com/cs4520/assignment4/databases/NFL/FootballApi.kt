package com.cs4520.assignment4.databases.NFL

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballApi {
    const val Football_URL: String = "https://v1.american-football.api-sports.io"
    const val ENDPOINT: String = "games"
    val retrofit = Retrofit.Builder()
        .baseUrl(Football_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val sportsApi = retrofit.create(IFootballApi::class.java)
}