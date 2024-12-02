package com.example.flixsterpart2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val movieId: String,
    val text: String,
    val rating: Float,
    val timestamp: Long = System.currentTimeMillis()
)
