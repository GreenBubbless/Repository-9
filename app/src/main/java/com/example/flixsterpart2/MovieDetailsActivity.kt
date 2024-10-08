package com.example.flixsterpart2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieTitle: TextView
    private lateinit var movieOverview: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieReleaseDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieTitle = findViewById(R.id.movieTitle)
        movieOverview = findViewById(R.id.movieOverview)
        moviePoster = findViewById(R.id.moviePoster)
        movieReleaseDate = findViewById(R.id.movieReleaseDate)

        val title = intent.getStringExtra("MOVIE_TITLE")
        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
        val posterPath = intent.getStringExtra("MOVIE_POSTER")
        val releaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")

        movieTitle.text = title
        movieOverview.text = overview
        movieReleaseDate.text = releaseDate

        // Load the movie poster using Glide
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .into(moviePoster)
    }
}


