package com.hoopCarpool.movies.presentation.usescases.favoritesMovies

import com.hoopCarpool.movies.domain.model.Movie

data class FavoriteMoviesState (
    val isLoading: Boolean = false,
    val favoritesMovies: List<Movie> = emptyList(),
    val favoritesMoviesArraylist: ArrayList<Movie> = ArrayList()
)