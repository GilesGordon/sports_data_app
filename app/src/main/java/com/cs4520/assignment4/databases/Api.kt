package com.cs4520.assignment4.databases

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASKETBALL_URL: String = "https://v1.basketball.api-sports.io"
    const val ENDPOINT: String = "games"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASKETBALL_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val sportsApi = retrofit.create(SportsApi::class.java)
}