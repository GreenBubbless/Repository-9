package com.example.flixsterpart2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    var movies: List<Movie>? = null

    fun fetchMovies(apiKey: String) {
        viewModelScope.launch {
            movies = MovieRepository.fetchPopularMovies(apiKey)
        }
    }
}
