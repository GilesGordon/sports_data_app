package com.cs4520.assignment4

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod"
    val retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val productApi = retrofit.create(ProductApi::class.java)
}