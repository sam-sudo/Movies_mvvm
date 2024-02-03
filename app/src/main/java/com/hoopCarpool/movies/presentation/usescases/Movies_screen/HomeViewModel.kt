package com.hoopCarpool.movies.presentation.usescases.Movies_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopCarpool.movies.domain.model.Movie
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import com.hoopCarpool.util.Constants
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.movies.presentation.util.MovieSharedPreferencesHelper
import com.hoopCarpool.movies.presentation.util.sendEvent
import com.hoopCarpool.util.Event
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesViewState())
    val state = _state.asStateFlow()

    //var movieProvider = MoviesProvider( context)

    /*private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies*/

    private var moviesInmutable = listOf<Movie>()

    /*private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading*/

    val movieSharedPreferencesHelper = MovieSharedPreferencesHelper(context)

    private var previousNumber: Int? = null


    init {
        loadMovies()
    }
    fun getMoviesList(): List<Movie> {
        return state.value.movies
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
        //_movies.value = updatedList
        _state.update {
            it.copy(
                movies = updatedList
            )
        }
    }


    fun getMoviesByTitle(title: String) {
        if (title.isNotEmpty()) {
            val filteredMovies = moviesInmutable.filter { movie ->
                movie.title.contains(title, ignoreCase = true)
            }
            //_movies.postValue(filteredMovies)
            _state.update {
                it.copy(
                    movies = filteredMovies
                )
            }
        } else {
            //_movies.value = moviesInmutable
            _state.update {
                it.copy(
                    movies = moviesInmutable
                )
            }
        }
    }

    fun loadMovies(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            moviesRepository.getPopularMoviesByPage(1)
                .onRight {response ->

                    var movies = response.body()?.results

                    if (response.isSuccessful && !movies.isNullOrEmpty()) {

                        movies = movies.map { movie ->

                            var movieDetail = getMovieDetail(movie.id)

                            var duration = movieDetail?.duration

                            movie.copy(
                                imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath,
                                backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path,
                                duration = duration ?: 0.0
                            )

                        }

                        var moviesWitFavorites = checkFavoritesMovies(movies)


                        if (state.value.movies != moviesWitFavorites) {
                             _state.update {
                                 it.copy(
                                     movies = moviesWitFavorites
                                 )
                             }
                            moviesInmutable = moviesWitFavorites
                        }


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

    private suspend fun getMovieDetail(id : String): Movie? {
        moviesRepository.getMovieById(id= id)
            .onRight {response ->

                val movieDetail = response.body()

                return if (response.isSuccessful) {
                    Log.w("TAG", "movieDetail:  ${movieDetail}", )
                    movieDetail
                } else {
                    Log.w("TAG", "response.code(): ${response.code()} ", )
                    when (response.code()) {
                        else -> {
                            null
                        }
                    }
                }

            }
            .onLeft {error ->
                _state.update {
                    it.copy(
                        error = error.error.message
                    )
                }
            }

        return null
    }

    /*fun loadMovies(){
        *//*var newMovies = movies.ge
        _movies.postValue(newMovies)
        movies = ArrayList(newMovies)*//*

        _isLoading.value = true
        viewModelScope.launch {
            try {

                var movies = movieProvider.getPopularMoviesByPage(1)

                var moviesWitFavorites = checkFavoritesMovies(movies)
                _isLoading.value = false
                if (_movies.value != moviesWitFavorites) {
                    _movies.value = moviesWitFavorites
                }
                moviesInmutable = moviesWitFavorites

            }catch (e: Exception){
                Log.e("TAG", "loadMovies exeption ${e.message} ", )
            }
        }

    }*/

    fun loadRandomMovies(){
        var randomPage = getRandomNumber()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            moviesRepository.getPopularMoviesByPage(randomPage)
                .onRight {response ->

                    var movies = response.body()?.results

                    if (response.isSuccessful && !movies.isNullOrEmpty()) {

                        movies = movies.map { movie ->

                            var movieDetail = getMovieDetail(movie.id)

                            var duration = movieDetail?.duration

                            movie.copy(
                                imageUrl = Constants.API_URL_MOVIES_IMAGES + movie.imagePath ?: "",
                                backdrop_pathUrl = Constants.API_URL_MOVIES_IMAGES + movie.backdrop_path ?: "",
                                duration = duration ?: 0.0
                            )

                        }

                        var moviesWitFavorites = checkFavoritesMovies(movies)


                        if (state.value.movies != moviesWitFavorites) {
                            _state.update {
                                it.copy(
                                    movies = moviesWitFavorites
                                )
                            }
                            moviesInmutable = moviesWitFavorites
                        }


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