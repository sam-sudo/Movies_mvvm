package com.hoopCarpool.movies.usescases.home

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(private val context: Context) : ViewModel() {

    var movieProvider = MoviesProvider( context)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private var moviesInmutable = listOf<Movie>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)

    private var previousNumber: Int? = null


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

        _isLoading.value = true
        viewModelScope.launch {
            try {

                var movies = movieProvider.getPopularMoviesByPage(1)

                Log.w("TAG", "loadMovies: $movies", )



                /*movies = movies.map { movie ->
                    movie.copy(
                        imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                        backdrop_pathUrl =Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path
                    )

                }*/

                var moviesWitFavorites = checkFavoritesMovies(movies)
                Log.w("TAG", "moviesWitFavorites: $moviesWitFavorites ", )
                _isLoading.value = false
                if (_movies.value != moviesWitFavorites) {
                    _movies.value = moviesWitFavorites
                }
                moviesInmutable = moviesWitFavorites
            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }

    }

    fun loadRandomMovies(){
        var randomPage = getRandomNumber()
        Log.w("tag", "getRandomNumber: ${getRandomNumber()}", )

        _isLoading.value = true

        viewModelScope.launch {
            try {

                var movies = movieProvider.getPopularMoviesByPage(randomPage)

                Log.w("TAG", "loadMovies: $movies", )



                /*movies = movies.map { movie ->
                    movie.copy(
                        imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                        backdrop_pathUrl =Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path
                    )

                }*/

                var moviesWitFavorites = checkFavoritesMovies(movies)
                Log.w("TAG", "moviesWitFavorites: $moviesWitFavorites ", )

                //delay(3000)
                //_movies.value = moviesWitFavorites
                if (_movies.value != moviesWitFavorites) {
                    _movies.value = moviesWitFavorites
                }
                _isLoading.value = false
                moviesInmutable = moviesWitFavorites
            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }

    }

    fun getRandomNumber(): Int {
        var randomNumber: Int
        do {
            randomNumber = (1..10).random()
        } while (randomNumber == previousNumber)

        previousNumber = randomNumber
        return randomNumber
    }

    /*fun getRandomMovie(): Movie{
        return MoviesProvider.random()
    }*/

}