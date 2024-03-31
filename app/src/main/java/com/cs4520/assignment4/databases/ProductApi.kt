package com.cs4520.assignment4.databases

import com.cs4520.assignment4.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET(Api.ENDPOINT)
    suspend fun getProducts(
        @Query("page") page: Int? = null
    ): List<Product>
}