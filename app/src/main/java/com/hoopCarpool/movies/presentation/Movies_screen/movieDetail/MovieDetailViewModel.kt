package com.hoopCarpool.movies.presentation.Movies_screen.movieDetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.util.Constants
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val context: Context) :ViewModel(){

    var movieProvider = MoviesProvider(context)


    private val _movieItem = MutableLiveData<Movie>()
    val movieItem: LiveData<Movie> get() = _movieItem

    val favoriteStatus = MutableLiveData<Boolean>()

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading = _isLoading

    fun loadDetailData(id: String) {


        _isLoading.value = true
        viewModelScope.launch {
            var movie = movieProvider.getMovieDetail(id)

            val isFavorite = movieProvider.isFavorite(id)

            if (movie != null) {
                Log.w("TAG", "movie before detailll: $movie ", )

                movie = movie.copy(
                    favorite = isFavorite,
                    imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                    backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path
                )

                Log.w("TAG", "movie detailll: $movie ", )

                favoriteStatus.postValue(movie.favorite)
                _movieItem.value = movie
                Log.w("TAG", "_movieItem.value: ${_movieItem.value}", )
            }

            _isLoading.value = false

        }


    }


}