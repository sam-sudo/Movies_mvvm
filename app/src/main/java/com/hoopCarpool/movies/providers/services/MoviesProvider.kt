package com.hoopCarpool.movies.providers.services

import com.hoopCarpool.movies.model.Movie

class MoviesProvider {

    //private val movieService = RetrofitIntance.getMoviesService

    suspend fun getMovies(): List<Movie>{
        var urlTemp = "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_FMjpg_UX1000_.jpg"

        return listOf(
            Movie("Item 1", "Subtitle 1", urlTemp, 8.5, "2021", "1h 30m", true),
            Movie("Item 2", "Subtitle 2", urlTemp, 7.8, "2022", "1h 45m", false),
            Movie("Item 3", "Subtitle 3", urlTemp, 9.0, "2023", "2h 10m", true),
            Movie("Item 4", "Subtitle 4", "", 8.2, "2024", "1h 55m", false),
            Movie("Item 5", "Subtitle 5", urlTemp, 8.9, "2025", "2h 5m", true),
            Movie("Item 6", "Subtitle 6", urlTemp, 7.5, "2026", "1h 40m", false),
            Movie("Item 7", "Subtitle 7", urlTemp, 9.3, "2027", "2h 20m", true),
            Movie("Item 8", "Subtitle 8", urlTemp, 7.0, "2028", "1h 50m", false),
            Movie("Item 9", "Subtitle 9", urlTemp, 8.7, "2029", "2h", true),
            Movie("Item 10", "Subtitle 10", urlTemp, 7.4, "2030", "1h 55m", false)
            // ... agregar más elementos según sea necesario
        )
    }
}