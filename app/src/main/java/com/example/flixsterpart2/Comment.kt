package com.example.flixsterpart2

data class Comment(
    val text: String,
    val rating: Float,
    val timestamp: Long = System.currentTimeMillis() // Automatically sets the time of creation
)