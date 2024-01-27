package com.hoopCarpool.movies.model

data class Movie(
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val stars: Double,
    val year: String,
    val duration: String,
    val favorite: Boolean = false
)
