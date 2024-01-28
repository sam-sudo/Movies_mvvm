package com.hoopCarpool.movies.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hoopCarpool.movies.model.Movie
import com.hoopCarpool.movies.usescases.home.HomeActivity
import com.hoopCarpool.movies.usescases.home.HomeScreen
import com.hoopCarpool.movies.usescases.home.MovieDetailScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(
            route = AppScreens.MovieDetailScreen.route + "/{movieId}",
            arguments = listOf(navArgument(name = "movieId"){
                type = NavType.StringType
            })
        ){

            val movieId = it.arguments?.getString("movieId")

            requireNotNull(movieId,{ "No puede ser null, por que el detalla siempre necesita ID"})
            MovieDetailScreen(navController, movieId)
        }
    }

}