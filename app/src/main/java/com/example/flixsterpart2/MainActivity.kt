package com.example.flixsterpart2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(movies) { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_TITLE", movie.title)
                putExtra("MOVIE_OVERVIEW", movie.overview)
                putExtra("MOVIE_POSTER", movie.poster_path)
                putExtra("MOVIE_RELEASE_DATE", movie.release_date) // Add release date
            }
            startActivity(intent)
        }

        recyclerView.adapter = movieAdapter
        fetchMovies()
    }

    private fun fetchMovies() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")

                    val gson = Gson()
                    val type = object : TypeToken<MovieResponse>() {}.type
                    val movieResponse = gson.fromJson<MovieResponse>(it.body?.string(), type)
                    runOnUiThread {
                        movies.clear()
                        movies.addAll(movieResponse.results)
                        movieAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
