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
    val movies: LiveData<List<Movie>> = _movies


    init {
        loadMovies()
    }


    /*fun addMovies(){
        //movies.add(Movie("nueva","nueva sub", Icons.Default.Add))
        _movies.postValue(movies)
    }*/

    fun getMoviesSize(): Int?{
        return movies.value?.size
    }

    fun loadMovies(){
        /*var newMovies = movies.ge
        _movies.postValue(newMovies)
        movies = ArrayList(newMovies)*/

        viewModelScope.launch {
            try {
                val movies = movieProvider.getMovies()
                _movies.value = movies
            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }
    }

    /*fun getRandomMovie(): Movie{
        return MoviesProvider.random()
    }*/

}