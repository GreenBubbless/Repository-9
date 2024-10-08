package com.example.flixsterpart2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>
}
