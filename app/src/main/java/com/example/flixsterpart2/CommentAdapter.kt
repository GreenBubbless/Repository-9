package com.example.flixsterpart2

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class CommentAdapter(private val comments: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentText: TextView = itemView.findViewById(R.id.commentText)
        private val commentRating: RatingBar = itemView.findViewById(R.id.commentRating)

        fun bind(comment: Comment) {
            commentText.text = comment.text

            commentRating.setOnTouchListener(null)
            commentRating.setOnRatingBarChangeListener(null)

            commentRating.rating = comment.rating

            commentRating.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val ratingBar = view as RatingBar
                    val widthPerStar = ratingBar.width.toFloat() / ratingBar.numStars
                    val touchRating = (event.x / widthPerStar).toFloat()

                    // Snap the rating to the nearest 0.5 or full star
                    val snappedRating = snapToNearestHalfStar(touchRating)
                    comment.rating = snappedRating // Update the data model
                    ratingBar.rating = snappedRating // Update the UI

                    // Notify RecyclerView about the change
                    notifyItemChanged(bindingAdapterPosition)

                    // Perform click for accessibility tools
                    view.performClick()
                }
                true // Indicate the event was handled
            }

            // Listener to sync programmatic changes
            commentRating.setOnRatingBarChangeListener { _, rating, _ ->
                val snappedRating = snapToNearestHalfStar(rating)
                if (comment.rating != snappedRating) {
                    comment.rating = snappedRating
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }

        // Helper function to snap ratings to the nearest 0.5
        private fun snapToNearestHalfStar(rating: Float): Float {
            return (rating * 2).roundToInt() / 2.0f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size
}
