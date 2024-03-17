package com.cs4520.assignment4

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET(Api.ENDPOINT)
    suspend fun getProducts(
        @Query("page") page: Int? = null
    ): List<Product>
}