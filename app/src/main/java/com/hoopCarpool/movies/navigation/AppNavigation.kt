package com.hoopCarpool.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hoopCarpool.movies.usescases.favoritesMovies.FavoriteMoviesScreen
import com.hoopCarpool.movies.usescases.favoritesMovies.FavoriteViewModel
import com.hoopCarpool.movies.usescases.home.HomeScreen
import com.hoopCarpool.movies.usescases.home.HomeViewModel
import com.hoopCarpool.movies.usescases.common.Components.movieDetail.MovieDetailScreen
import com.hoopCarpool.movies.usescases.common.Components.movieDetail.MovieDetailViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val homeViewModel: HomeViewModel = HomeViewModel(LocalContext.current)
    val movieDetailViewModel: MovieDetailViewModel = MovieDetailViewModel(LocalContext.current)
    var favoriteViewModel : FavoriteViewModel = FavoriteViewModel(LocalContext.current)


    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController,homeViewModel)
        }
        composable(
            route = AppScreens.MovieDetailScreen.route + "/{movieId}",
            arguments = listOf(navArgument(name = "movieId"){
                type = NavType.StringType
            })
        ){

            val movieId = it.arguments?.getString("movieId")

            requireNotNull(movieId,{ "No puede ser null, por que el detalla siempre necesita ID"})
            MovieDetailScreen(navController, movieId, homeViewModel,favoriteViewModel,movieDetailViewModel)
        }
        composable(route = AppScreens.FavoriteMoviesScreen.route){
            FavoriteMoviesScreen(navController,favoriteViewModel)
        }
    }

}