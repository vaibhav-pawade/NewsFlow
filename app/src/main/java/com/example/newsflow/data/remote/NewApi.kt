package com.example.newsflow.data.remote



import retrofit2.http.GET
import retrofit2.http.Query

interface NewApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apikey") apiKey: String
    ): NewsResponse
}