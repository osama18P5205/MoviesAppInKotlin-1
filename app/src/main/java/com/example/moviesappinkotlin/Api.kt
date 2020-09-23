package com.example.moviesappinkotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey : String = "4b78badbf5f82fa122e265958a4143cc",
        @Query("page") pageNumber: Int = 1
    ) : Call<Response>
}