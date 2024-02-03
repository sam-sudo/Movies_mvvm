package com.hoopCarpool.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hoopCarpool.movies.presentation.usescases.favoritesMovies.FavoriteMoviesScreen
import com.hoopCarpool.movies.presentation.usescases.favoritesMovies.FavoriteViewModel
import com.hoopCarpool.movies.presentation.usescases.Movies_screen.HomeScreen
import com.hoopCarpool.movies.presentation.usescases.Movies_screen.HomeViewModel
import com.hoopCarpool.movies.presentation.usescases.Movies_screen.movieDetail.MovieDetailScreen
import com.hoopCarpool.movies.presentation.usescases.Movies_screen.movieDetail.MovieDetailViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val homeViewModel: HomeViewModel = hiltViewModel()
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