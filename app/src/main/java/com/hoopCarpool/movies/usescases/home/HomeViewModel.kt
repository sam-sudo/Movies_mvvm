package com.hoopCarpool.movies.usescases.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.movies.util.MovieSharedPreferencesHelper
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context) : ViewModel() {

    var movieProvider = MoviesProvider()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private var moviesInmutable = listOf<Movie>()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading

    val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)
    init {
        loadMovies()
    }
    fun getMoviesList(): LiveData<List<Movie>> {
        return movies
    }
    fun getMoviesInmutable(): List<Movie> {
        return moviesInmutable
    }


    suspend fun checkFavoritesMovies(movies: List<Movie>): List<Movie> {
        val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()

        return movies.map { movie ->
            // Utiliza Set.contains para verificar si el ID de la película está en el conjunto
            movie.copy(favorite = favoriteMoviesSet.contains(movie.id))
        }
    }


    fun updateMovie(updatedMovie: Movie) {
        val updatedList = moviesInmutable.map { if (it.id == updatedMovie.id) updatedMovie else it }
        moviesInmutable = updatedList

        moviesInmutable = updatedList
        _movies.value = updatedList
    }


    fun getMoviesByTitle(title: String) {
        if (title.isNotEmpty()) {
            val filteredMovies = moviesInmutable.filter { movie ->
                movie.title.contains(title, ignoreCase = true)
            }
            _movies.postValue(filteredMovies)
        } else {
            _movies.value = moviesInmutable
        }
    }

    fun loadMovies(){
        /*var newMovies = movies.ge
        _movies.postValue(newMovies)
        movies = ArrayList(newMovies)*/

        viewModelScope.launch {
            try {
                _isLoading.value = true
                var movies = movieProvider.getMovies()

                var moviesWitFavorites = checkFavoritesMovies(movies)

                _isLoading.value = false

                _movies.value = moviesWitFavorites
                moviesInmutable = moviesWitFavorites
            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }

    }


    /*fun getRandomMovie(): Movie{
        return MoviesProvider.random()
    }*/

}