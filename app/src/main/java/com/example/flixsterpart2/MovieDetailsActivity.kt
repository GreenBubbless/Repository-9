package com.example.flixsterpart2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView

import android.content.SharedPreferences
import com.google.gson.Gson

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieTitle: TextView
    private lateinit var movieOverview: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieReleaseDate: TextView
    private lateinit var commentsRecyclerView: RecyclerView
    private lateinit var addCommentButton: Button
    private lateinit var commentInput: EditText
    private lateinit var ratingBar: RatingBar
    private val commentList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val movieId: String by lazy { intent.getStringExtra("MOVIE_TITLE") ?: "default_movie" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieTitle = findViewById(R.id.movieTitle)
        movieOverview = findViewById(R.id.movieOverview)
        moviePoster = findViewById(R.id.moviePoster)
        movieReleaseDate = findViewById(R.id.movieReleaseDate)
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)
        addCommentButton = findViewById(R.id.addCommentButton)
        commentInput = findViewById(R.id.commentInput)
        ratingBar = findViewById(R.id.ratingBar)

        sharedPreferences = getSharedPreferences("movie_comments", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val title = intent.getStringExtra("MOVIE_TITLE")
        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
        val posterPath = intent.getStringExtra("MOVIE_POSTER")
        val releaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")

        movieTitle.text = title
        movieOverview.text = overview
        movieReleaseDate.text = releaseDate

        // Load the comments for the current movie
        loadComments()

        // Load the movie poster using Glide
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .into(moviePoster)

        // Set up RecyclerView for comments
        commentAdapter = CommentAdapter(commentList)
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentsRecyclerView.adapter = commentAdapter

        // Handle Add Comment Button click
        addCommentButton.setOnClickListener {
            val commentText = commentInput.text.toString()
            val rating = ratingBar.rating

            if (commentText.isNotEmpty()) {
                val comment = Comment(commentText, rating)
                commentList.add(comment)
                commentAdapter.notifyItemInserted(commentList.size - 1)

                // Save the new comment
                saveComments()

                // Clear the input fields
                commentInput.text.clear()
                ratingBar.rating = 0f

                Toast.makeText(this, "Comment added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a comment.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadComments() {
        // Fetch the saved comments for this movie from SharedPreferences
        val savedComments = sharedPreferences.getString(movieId, null)
        savedComments?.let {
            // If comments are saved, deserialize and add them to the list
            val comments = Gson().fromJson(savedComments, Array<Comment>::class.java).toList()
            commentList.addAll(comments)
            commentAdapter.notifyDataSetChanged()
        }
    }

    private fun saveComments() {
        // Serialize the list of comments and save it to SharedPreferences
        val jsonComments = Gson().toJson(commentList)
        editor.putString(movieId, jsonComments).apply()
    }
}

//package com.example.flixsterpart2
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.RatingBar
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import android.widget.ImageView
//import android.widget.TextView
//
//import android.content.SharedPreferences
//import com.google.gson.Gson
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class MovieDetailsActivity : AppCompatActivity() {
//    private lateinit var movieTitle: TextView
//    private lateinit var movieOverview: TextView
//    private lateinit var moviePoster: ImageView
//    private lateinit var movieReleaseDate: TextView
//    private lateinit var commentsRecyclerView: RecyclerView
//    private lateinit var addCommentButton: Button
//    private lateinit var commentInput: EditText
//    private lateinit var ratingBar: RatingBar
//    private val commentList = mutableListOf<Comment>()
//    private lateinit var commentAdapter: CommentAdapter
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var editor: SharedPreferences.Editor
//    private val movieId: String by lazy { intent.getStringExtra("MOVIE_TITLE") ?: "default_movie" }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_movie_details)
//
//        movieTitle = findViewById(R.id.movieTitle)
//        movieOverview = findViewById(R.id.movieOverview)
//        moviePoster = findViewById(R.id.moviePoster)
//        movieReleaseDate = findViewById(R.id.movieReleaseDate)
//        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)
//        addCommentButton = findViewById(R.id.addCommentButton)
//        commentInput = findViewById(R.id.commentInput)
//        ratingBar = findViewById(R.id.ratingBar)
//
//        sharedPreferences = getSharedPreferences("movie_comments", MODE_PRIVATE)
//        editor = sharedPreferences.edit()
//
//        val title = intent.getStringExtra("MOVIE_TITLE")
//        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
//        val posterPath = intent.getStringExtra("MOVIE_POSTER")
//        val releaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")
//
//        movieTitle.text = title
//        movieOverview.text = overview
//        movieReleaseDate.text = releaseDate
//
//        // Load the comments for the current movie
//        loadComments()
//
//        // Load the movie poster using Glide
//        Glide.with(this)
//            .load("https://image.tmdb.org/t/p/w500$posterPath")
//            .into(moviePoster)
//
//        // Set up RecyclerView for comments
//        commentAdapter = CommentAdapter(commentList)
//        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
//        commentsRecyclerView.adapter = commentAdapter
//
//        // Handle Add Comment Button click
//        addCommentButton.setOnClickListener {
//            val commentText = commentInput.text.toString()
//            val rating = ratingBar.rating
//
//            if (commentText.isNotEmpty()) {
//                val comment = CommentEntity(
//                    movieId = movieId,
//                    text = commentText,
//                    rating = rating
//                )
//                CoroutineScope(Dispatchers.IO).launch {
//                    DatabaseProvider.getDatabase(this@MovieDetailsActivity).commentDao().insert(comment)
//                    runOnUiThread {
//                        loadComments()  // Reload comments from database
//                    }
//                }
//
//                // Clear the input fields
//                commentInput.text.clear()
//                ratingBar.rating = 0f
//                Toast.makeText(this, "Comment added!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Please enter a comment.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }
//
//    private fun loadComments() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val commentEntities = DatabaseProvider.getDatabase(this@MovieDetailsActivity).commentDao().getCommentsByMovieId(movieId)
//            val comments = commentEntities.map { entity ->
//                Comment(
//                    text = entity.text,
//                    rating = entity.rating
//                )
//            }
//            runOnUiThread {
//                commentList.clear()
//                commentList.addAll(comments)
//                commentAdapter.notifyDataSetChanged()
//            }
//        }
//    }
////    private fun loadComments() {
////        CoroutineScope(Dispatchers.IO).launch {
////            val comments = DatabaseProvider.getDatabase(this@MovieDetailsActivity).commentDao().getCommentsByMovieId(movieId)
////            runOnUiThread {
////                commentList.clear()
////                commentList.addAll(comments)
////                commentAdapter.notifyDataSetChanged()
////            }
////        }
////    }
//
//    private fun saveComments() {
//        // Serialize the list of comments and save it to SharedPreferences
//        val jsonComments = Gson().toJson(commentList)
//        editor.putString(movieId, jsonComments).apply()
//    }
//}
