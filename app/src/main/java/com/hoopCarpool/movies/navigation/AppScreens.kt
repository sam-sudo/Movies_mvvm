package com.hoopCarpool.movies.navigation

sealed class AppScreens(val route: String){

    object HomeScreen: AppScreens("home_screen")
    object MovieDetailScreen: AppScreens("detail_movie_screen")

}
