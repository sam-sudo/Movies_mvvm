package com.hoopCarpool.movies.presentation.usescases.Movies_screen.movieDetail

import com.hoopCarpool.movies.domain.model.Movie

data class MovieDetailState(
    val movieItem: Movie? = null,
    val isLoading: Boolean = false,
    val favoriteStatus: Boolean = false,
    val error: String? = null,
)
