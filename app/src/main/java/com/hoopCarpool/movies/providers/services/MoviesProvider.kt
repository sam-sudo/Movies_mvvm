package com.hoopCarpool.movies.providers.services

import android.content.Context
import android.util.Log
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.util.Constants
import com.hoopCarpool.movies.util.MovieSharedPreferencesHelper

class MoviesProvider(private val context: Context) {



    val apiService: ApiService = RetrofitInstance.retrofit.create(ApiService::class.java)
    private val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)

    suspend fun getPopularMoviesByPage(page : Int): List<Movie> {
        val response = apiService.getPopularMoviesByPage(page = page)
        var movieList = response.body()?.results
        return if (response.isSuccessful) {
            Log.w("TAG", "response:  ${movieList}", )
            movieList = movieList?.map { movie ->

                var movieDetail = getMovieDetail(movie.id)
                Log.w("TAG", "movie:  $movie ", )

                var duration = movieDetail?.duration

                movie.copy(
                    imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                    backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path,
                    duration = duration ?: 0.0
                )

            }


            movieList ?: emptyList()
        } else {
            Log.w("TAG", "response.code(): ${response.code()} ", )
            when (response.code()) {
                else -> {
                    emptyList()
                }
            }
        }
    }

    suspend fun getMovieDetail(id : String): Movie? {
        val response = apiService.getMovieById(id= id)
        val movieDetail = response.body()
        return if (response.isSuccessful) {
            Log.w("TAG", "movieDetail:  ${movieDetail}", )
            movieDetail
        } else {
            Log.w("TAG", "response.code(): ${response.code()} ", )
            when (response.code()) {
                else -> {
                    null
                }
            }
        }
    }

    suspend fun isFavorite(id: String): Boolean{

        val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()

        return favoriteMoviesSet.contains(id)

    }

    /*suspend fun getMovies(): List<Movie>{
        var urlTemp = "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_FMjpg_UX1000_.jpg"

        return listOf(
            Movie("0","Item 1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum.\n" +
                    "\n", urlTemp, 8.5, "2021", "1h 30m", false),
            Movie("1","Item 2", "Subtitle 2", urlTemp, 7.8, "2022", "1h 45m", false),
            Movie("2","Item 3", "Subtitle 3", urlTemp, 9.0, "2023", "2h 10m", false),
            Movie("3","Item 4", "Subtitle 4", "", 8.2, "2024", "1h 55m", false),
            Movie("4","Item 5", "Subtitle 5", urlTemp, 8.9, "2025", "2h 5m", false),
            Movie("5","Item 6", "Subtitle 6", urlTemp, 7.5, "2026", "1h 40m", false),
            Movie("6","Item 7", "Subtitle 7", urlTemp, 9.3, "2027", "2h 20m", false),
            Movie("7","Item 8", "Subtitle 8", urlTemp, 7.0, "2028", "1h 50m", false),
            Movie("8","Item 9", "Subtitle 9", urlTemp, 8.7, "2029", "2h", false),
            Movie("9","Item 10", "Subtitle 10", urlTemp, 7.4, "2030", "1h 55m", false)
            // ... agregar más elementos según sea necesario
        )
    }*/
}