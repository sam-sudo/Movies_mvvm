package com.hoopCarpool.movies.usescases.favoritesMovies

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.movies.util.Constants
import com.hoopCarpool.movies.util.MovieSharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val context: Context): ViewModel() {

    private val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)

    var movieProvider = MoviesProvider(context)


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
            //var moviesSet = movieProvider.getPopularMoviesByPage(1)



            val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()
            val favoriteList = ArrayList<Movie>()

            Log.w("TAG", "loadFavoriteMovies: $favoriteMoviesSet", )
            favoriteMoviesSet.forEach{movieId ->

                var movie = movieProvider.getMovieDetail(movieId)
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