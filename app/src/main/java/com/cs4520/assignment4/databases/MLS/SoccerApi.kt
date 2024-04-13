package com.cs4520.assignment4.databases.MLS

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SoccerApi {
    const val SOCCER_URL: String = "https://v3.football.api-sports.io"
    const val ENDPOINT: String = "fixtures"
    val retrofit = Retrofit.Builder()
        .baseUrl(SOCCER_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val sportsApi = retrofit.create(ISoccerApi::class.java)
}