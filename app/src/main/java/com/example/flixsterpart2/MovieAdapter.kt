package com.example.flixsterpart2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit // Callback function for clicks
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title

            // Load the movie poster using Glide (or any other image loading library)
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(moviePoster)

            // Set up the click listener
            itemView.setOnClickListener {
                onClick(movie) // Trigger the callback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size
}
