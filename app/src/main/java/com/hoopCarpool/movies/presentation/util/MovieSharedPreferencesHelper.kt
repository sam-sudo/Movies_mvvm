package com.hoopCarpool.movies.presentation.util

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.*
import com.hoopCarpool.movies.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.prefs.Preferences
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


private val Context.dataStore by preferencesDataStore(name = "settings")


class MovieSharedPreferencesHelper(context: Context) {
    private val dataStore = context.dataStore

    suspend fun getFavoriteMovies(): Set<String> {
        return dataStore.data.map { preferences ->
            preferences[FAVORITE_MOVIES_KEY] ?: emptySet()
        }.first()
    }

    suspend fun addToFavorites(movieId: String) {
        dataStore.edit { preferences ->
            val currentSet = preferences[FAVORITE_MOVIES_KEY] ?: emptySet()
            if ( !currentSet.contains(movieId)) {
                Log.w("TAG", "addToFavorites: to data storage", )
                preferences[FAVORITE_MOVIES_KEY] = currentSet + movieId
            }
        }
        Log.w("TAG", "addToFavorites: ${getFavoriteMovies()}", )
    }

    suspend fun removeFromFavorites(movieId: String) {
        Log.w("TAG", "before removeFromFavorites: ${getFavoriteMovies()}", )
        dataStore.edit { preferences ->
            val currentSet = preferences[FAVORITE_MOVIES_KEY] ?: emptySet()
            Log.w("TAG", "currentSet: $currentSet ", )
            if (currentSet.contains(movieId)) {
                Log.w("TAG", "removeFromFavorites: ", )
                preferences[FAVORITE_MOVIES_KEY] = currentSet - movieId
            }
        }
        Log.w("TAG", "removeFromFavorites: ${getFavoriteMovies()}", )
    }

    suspend fun addListToFavorites(movieList: List<Movie>) {

        GlobalScope.launch(Dispatchers.IO) {
            movieList.forEach {movie ->
                dataStore.edit { preferences ->
                    val currentSet = preferences[FAVORITE_MOVIES_KEY] ?: emptySet()
                    if(preferences[FAVORITE_MOVIES_KEY]?.contains(movie.id) == false){
                        preferences[FAVORITE_MOVIES_KEY] = currentSet + movie.id
                    }

                }
            }
        }




    }


    companion object {
        @Volatile
        private var INSTANCE: MovieSharedPreferencesHelper? = null

        fun getInstance(context: Context): MovieSharedPreferencesHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = MovieSharedPreferencesHelper(context)
                INSTANCE = instance
                instance
            }
        }

        private val FAVORITE_MOVIES_KEY = stringSetPreferencesKey("favorite_movies")
    }

}