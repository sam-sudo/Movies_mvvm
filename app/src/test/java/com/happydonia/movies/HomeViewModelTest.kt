package com.happydonia.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.providers.services.MoviesProvider
import com.hoopCarpool.movies.usescases.home.HomeViewModel
import com.hoopCarpool.movies.util.MovieSharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomeViewModelTest {




    // Regla para ejecutar tareas de la arquitectura de Android de manera síncrona
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel



    var urlTemp = "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_FMjpg_UX1000_.jpg"

    val mockMovies = listOf(
        Movie(
            "1",
            "Item 2",
            "Segundo texto de relleno para otra película.",
            "url2",
            urlTemp,
            "2022",
            "2h 5m",
            7.9,
            "2022-03-10",
            135.0,
            true
        ),
        Movie(
            "2",
            "Avengers: Endgame",
            "Tercer texto de relleno para otra película en la lista.",
            "url3",
            "",
            "2023",
            "1h 50m",
            8.3,
            "2022-08-25",
            110.0,
            false
        ),
        Movie(
            "3",
            "Avengers: Infinity War",
            "Cuarto texto de relleno para otra película.",
            "url4",
            "",
            "2024",
            "2h 20m",
            8.7,
            "2023-12-05",
            140.0,
            true
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        //val context = ApplicationProvider.getApplicationContext<Context>()
        val context = mock(Context::class.java)
        viewModel = HomeViewModel(context)



    }




    @Test
    fun `getMoviesByTitle should return filtered list with matching titles`() {
        // Given

        val moviesInmutable = mockMovies
        viewModel.moviesInmutable = moviesInmutable

        // When
        viewModel.getMoviesByTitle("Avengers")

        // Then
        val filteredMovies = viewModel.movies.value
        assertEquals(2, filteredMovies?.size) // Esperamos que haya 2 películas coincidentes
        assertEquals("Avengers: Endgame", filteredMovies?.get(0)?.title)
        assertEquals("Avengers: Infinity War", filteredMovies?.get(1)?.title)
    }

}