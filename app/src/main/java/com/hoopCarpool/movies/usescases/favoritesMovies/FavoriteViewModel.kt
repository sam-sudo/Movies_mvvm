package com.hoopCarpool.movies.usescases.favoritesMovies

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.movies.util.MovieSharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavoriteViewModel(private val context: Context): ViewModel() {

    private val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)

    var movieProvider = MoviesProvider()


    private val _favoriteMovies = MutableLiveData<List<Movie>>(emptyList())
    val favoriteMovies: LiveData<List<Movie>> get() = _favoriteMovies

    var favoriteMoviesArrayList = ArrayList<Movie>()

    init {
        loadFavoriteMovies()
    }


    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            Log.w("TAG", "addToFavorites: 1", )
            if(!favoriteMoviesArrayList.contains(movie)){
                Log.w("TAG", "addToFavorites: 2", )

                favoriteMoviesArrayList.add(movie)
                movieSharedPreferencesHelper.addToFavorites(movie.id)
            }
        }
        _favoriteMovies.value = favoriteMoviesArrayList
    }

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            Log.w("TAG", "movie: $movie", )
            Log.w("TAG", "removeFromFavorites: $favoriteMoviesArrayList", )
            Log.w(
                "TAG",
                "favoriteMoviesArrayList.contains(movie): ${
                    favoriteMoviesArrayList.contains(
                        movie
                    )
                }",
            )

            var isMovieInList = favoriteMoviesArrayList.find { it.id == movie.id }

            if(isMovieInList != null){
                favoriteMoviesArrayList.remove(movie)
                movieSharedPreferencesHelper.removeFromFavorites(movie.id)

                _favoriteMovies.postValue(favoriteMoviesArrayList)
            }

        }

    }

    /*private suspend fun saveFavoriteMovies(movies: List<Movie>) {
        movieSharedPreferencesHelper.addToFavorites(movies)
        _favoriteMovies.value = movies
    }*/

    fun loadFavoriteMovies() {
        viewModelScope.launch (Dispatchers.Main){
            val moviesSet = movieProvider.getMovies()
            val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()
            val favoriteList = ArrayList<Movie>()

            Log.w("TAG", "loadFavoriteMovies: $favoriteMoviesSet", )
            favoriteMoviesSet.forEach{movieId ->
                moviesSet.forEach {movie ->
                    if(movie.id == movieId){
                        movie.favorite = true
                        favoriteList.add(movie)
                    }
                }
            }

            favoriteMoviesArrayList = favoriteList
            _favoriteMovies.value = favoriteList
        }
    }

    fun updateMovie(updatedMovie: Movie) {
        val currentMovies = _favoriteMovies.value.orEmpty().toMutableList()
        val existingMovieIndex = currentMovies.indexOfFirst { it.id == updatedMovie.id }

        if (existingMovieIndex != -1) {
            currentMovies[existingMovieIndex] = updatedMovie
        }

        _favoriteMovies.value = currentMovies
    }



}