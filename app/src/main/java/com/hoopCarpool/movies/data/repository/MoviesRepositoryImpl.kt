package com.hoopCarpool.movies.data.repository

import arrow.core.Either
import com.hoopCarpool.movies.data.mapper.toNetworkError
import com.hoopCarpool.movies.data.remote.MoviesApi
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.model.NetworkError
import com.hoopCarpool.movies.domain.model.ResponsePopularMovies
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getPopularMoviesByPage(id: Int): Either<NetworkError, Response<ResponsePopularMovies>> {

        return Either.catch {
            moviesApi.getPopularMoviesByPage(page = id)
        }.mapLeft { it.toNetworkError() }
    }

    override suspend fun getMovieById(id: String): Either<NetworkError, Response<Movie>> {
        return Either.catch {
            moviesApi.getMovieById(id = id)
        }.mapLeft { it.toNetworkError() }
    }

}