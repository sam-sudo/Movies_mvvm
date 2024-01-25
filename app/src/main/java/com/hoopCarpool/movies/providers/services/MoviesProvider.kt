package com.hoopCarpool.movies.providers.services

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import com.hoopCarpool.movies.model.Movie
import kotlin.random.Random

class MoviesProvider {
    companion object{

        fun getMovies(): List<Movie>{
            return moviesTemp
        }

        fun random(): Movie{
            var randomNumber = Random(moviesTemp.size).nextInt()
            return moviesTemp[randomNumber]
        }




        private val moviesTemp = listOf(
            Movie("Item 1", "Subtitle 1", Icons.Default.Favorite),
            Movie("Item 2", "Subtitle 2", Icons.Default.ThumbUp),
            Movie("Item 3", "Subtitle 3", Icons.Default.Warning),
            Movie("Item 4", "Subtitle 4", Icons.Default.DateRange),
            Movie("Item 5", "Subtitle 4", Icons.Default.DateRange),
            Movie("Item 6", "Subtitle 4", Icons.Default.DateRange),
            Movie("Item 7", "Subtitle 4", Icons.Default.DateRange),
            Movie("Item 8", "Subtitle 4", Icons.Default.DateRange),
            Movie("Item 9", "Subtitle 4", Icons.Default.DateRange),
            // ... agregar más elementos según sea necesario
        )
    }
}