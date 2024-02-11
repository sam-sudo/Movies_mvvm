package com.hoopCarpool.movies.presentation.usescases.Movies_screen.movieDetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import com.hoopCarpool.movies.presentation.util.MovieSharedPreferencesHelper
import com.hoopCarpool.movies.presentation.util.sendEvent
import com.hoopCarpool.util.Constants
import com.hoopCarpool.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @ApplicationContext private val context: Context
) :ViewModel(){

    private val _state = MutableStateFlow(MovieDetailState())
    var state = _state.asStateFlow()

    private val movieSharedPreferencesHelper = MovieSharedPreferencesHelper.getInstance(context)

    fun updateFavoriteStatus(status: Boolean){
        _state.update {
            it.copy(
                favoriteStatus = status
            )
        }
    }

    fun loadDetailData(id: String) {

        _state.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            moviesRepository.getMovieById(id)
                .onRight {response ->
                    var movie = response.body()

                    val isFavorite = isFavorite(id)

                    if (movie != null) {
                        Log.w("TAG", "movie before detailll: $movie ", )

                        movie = movie.copy(
                            favorite = isFavorite,
                            imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                            backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path
                        )

                        Log.w("TAG", "movie detailll: $movie ", )


                        _state.update {
                            it.copy(
                                favoriteStatus = movie.favorite,
                                movieItem = movie
                            )
                        }
                        Log.w("TAG", "_movieItem.value: ${state.value.movieItem}", )
                    }



                }
                .onLeft {error ->
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }


            _state.update {
                it.copy(isLoading = false)
            }

        }


    }

    suspend fun isFavorite(id: String): Boolean{

        val favoriteMoviesSet = movieSharedPreferencesHelper.getFavoriteMovies()

        return favoriteMoviesSet.contains(id)

    }


}