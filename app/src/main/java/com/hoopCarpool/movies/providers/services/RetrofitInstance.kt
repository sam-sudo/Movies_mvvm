package com.hoopCarpool.movies.providers.services

import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.model.ResponsePopularMovies
import com.hoopCarpool.movies.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL_MOVIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()





}

interface ApiService {
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

