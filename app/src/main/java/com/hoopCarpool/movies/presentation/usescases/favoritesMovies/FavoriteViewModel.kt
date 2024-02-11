package com.hoopCarpool.movies.presentation.usescases.favoritesMovies

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import com.hoopCarpool.util.Constants
import com.hoopCarpool.movies.presentation.util.MovieSharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: MoviesRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteMoviesState())
    val state = _state.asStateFlow()

    private val movieSharedPreferencesHelper = MovieSharedPreferencesHelper.getInstance(context)


    init {
        loadFavoriteMovies()
    }


    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            Log.w("TAG", "addToFavorites: 1", )
            if(!state.value.favoritesMoviesArraylist.contains(movie)){
                Log.w("TAG", "addToFavorites: 2", )

                state.value.favoritesMoviesArraylist.add(movie)
                movieSharedPreferencesHelper.addToFavorites(movie.id)
            }
        }
        _state.update {
            it.copy(favoritesMoviesArraylist = state.value.favoritesMoviesArraylist)
        }
    }

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            Log.w("TAG", "movie: $movie", )
            Log.w("TAG", "removeFromFavorites: $state.value.favoritesMoviesArraylist", )
            Log.w(
                "TAG",
                "favoriteMoviesArrayList.contains(movie): ${
                    state.value.favoritesMoviesArraylist.contains(
                        movie
                    )
                }",
            )

            var isMovieInList = state.value.favoritesMoviesArraylist.find { it.id == movie.id }

            if(isMovieInList != null){
                state.value.favoritesMoviesArraylist.remove(movie)
                movieSharedPreferencesHelper.removeFromFavorites(movie.id)

                _state.update {
                    it.copy(favoritesMoviesArraylist = state.value.favoritesMoviesArraylist)
                }
            }

        }

    }

    /*private suspend fun saveFavoriteMovies(movies: List<Movie>) {
        movieSharedPreferencesHelper.addToFavorites(movies)
        _favoriteMovies.value = movies
    }*/

    fun loadFavoriteMovies() {
        viewModelScope.launch (Dispatchers.Main){
            //var moviesSet = movieProvider.getPopularMoviesByPage(1)



            val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()
            val favoriteList = ArrayList<Movie>()

            Log.w("TAG", "loadFavoriteMovies: $favoriteMoviesSet", )
            favoriteMoviesSet.forEach{movieId ->

                repository.getMovieById(movieId)
                    .onRight { response ->

                        var movie = response.body()


                        Log.w("TAG", "loadFavoriteMovies: before favorite $movie", )

                        movie = movie?.copy(
                            imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                            backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path,
                            favorite = true
                        )
                        if (movie != null) {
                            favoriteList.add(movie)
                        }

                        Log.w("TAG", "loadFavoriteMovies: favorite $movie", )
                    }
                    .onLeft {

                    }


            }


            _state.update {
                it.copy(
                    favoritesMovies = favoriteList,
                    favoritesMoviesArraylist = favoriteList
                )
            }
        }
    }

    fun updateMovie(updatedMovie: Movie) {
        val currentMovies = state.value.favoritesMovies.toMutableList()
        val existingMovieIndex = currentMovies.indexOfFirst { it.id == updatedMovie.id }

        if (existingMovieIndex != -1) {
            currentMovies[existingMovieIndex] = updatedMovie
        }


        _state.update {
            it.copy(
                favoritesMovies = currentMovies
            )
        }
    }



}