package com.hoopCarpool.movies.data.remote

import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.model.ResponsePopularMovies
import com.hoopCarpool.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/popular")
    suspend fun getPopularMoviesByPage(
        @Header("Authorization") authorization: String = "Bearer ${Constants.TOKEN_API_KEY}",
        @Query("page") page: Int
    ): Response<ResponsePopularMovies>


    @GET("3/movie/{id}")
    suspend fun getMovieById(
        @Header("Authorization") authorization: String = "Bearer ${Constants.TOKEN_API_KEY}",
        @Path("id") id: String
    ): Response<Movie>

}