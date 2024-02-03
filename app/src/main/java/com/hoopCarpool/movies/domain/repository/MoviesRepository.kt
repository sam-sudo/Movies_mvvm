package com.hoopCarpool.movies.domain.repository
import arrow.core.Either
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.model.NetworkError
import com.hoopCarpool.movies.domain.model.ResponsePopularMovies
import retrofit2.Response

interface MoviesRepository {
    suspend fun getPopularMoviesByPage(id: Int): Either<NetworkError,Response<ResponsePopularMovies>>
    suspend fun getMovieById(id: String): Either<NetworkError,Response<Movie>>
}