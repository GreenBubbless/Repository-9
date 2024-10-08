package com.example.flixsterpart2

import retrofit2.Response

object MovieRepository {
    private val apiService: ApiService = RetrofitInstance.instance.create(ApiService::class.java)

    // Using a suspend function to fetch data
    suspend fun fetchPopularMovies(apiKey: String): List<Movie>? {
        return try {
            val response: Response<MovieResponse> = apiService.getPopularMovies(apiKey)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}



