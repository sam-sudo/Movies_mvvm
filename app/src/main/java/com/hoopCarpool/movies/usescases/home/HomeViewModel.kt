package com.hoopCarpool.movies.usescases.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var movieProvider = MoviesProvider()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    var moviesInmutable = listOf<Movie>()

    init {
        loadMovies()
    }

    fun getMoviesList(): LiveData<List<Movie>> {
        return movies
    }


    /*fun addMovies(){
        //movies.add(Movie("nueva","nueva sub", Icons.Default.Add))
        _movies.postValue(movies)
    }*/

    /*fun getMoviesSize(): Int?{
        return movies.size
    }*/

    fun getMoviesByTitle(title: String) {
        if (title.isNotEmpty()) {
            val filteredMovies = moviesInmutable.filter { movie ->
                movie.title.contains(title, ignoreCase = true)
            }
            _movies.postValue(filteredMovies)
            Log.w("TAG", "getMoviesByTitle: ${_movies.value}", )
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

                var movies = movieProvider.getMovies()
                _movies.value = movies
                moviesInmutable = movies
            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }
    }

    /*fun getRandomMovie(): Movie{
        return MoviesProvider.random()
    }*/

}