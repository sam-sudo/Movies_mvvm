package com.hoopCarpool.movies.presentation.Movies_screen

import com.hoopCarpool.movies.domain.model.Movie

data class MoviesViewState(
    val isLoading: Boolean = false,
    val swipeRefreshState: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val searchText: String = "",
    val error: String? = null
)
