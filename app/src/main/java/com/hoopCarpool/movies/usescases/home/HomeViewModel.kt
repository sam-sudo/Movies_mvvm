package com.hoopCarpool.movies.usescases.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider

class HomeViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>(emptyList())

    init {
        loadMovies()
    }


    fun getMovies(): LiveData<List<Movie>>{
        return _movies
    }

    fun getMoviesSize(): Int?{
        return getMovies().value?.size
    }

    fun loadMovies(){
        _movies.postValue(MoviesProvider.getMovies())
    }

    fun getRandomMovie(): Movie{
        return MoviesProvider.random()
    }

}