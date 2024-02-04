package com.hoopCarpool.movies.presentation.usescases.Movies_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.domain.repository.MoviesRepository
import com.hoopCarpool.movies.presentation.navigation.AppScreens
import com.hoopCarpool.movies.presentation.usescases.Movies_screen.components.MovieListCard
import com.hoopCarpool.movies.presentation.util.components.LoadingAnimation
import com.hoopCarpool.movies.presentation.util.components.MyTopBar
import com.hoopCarpool.movies.presentation.util.components.SearchScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
){

    Log.w("TAG", "HomeScreen: ", )
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    MoviesContent(navController = navController,homeViewModel = homeViewModel, state = state)

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesContent(navController: NavController,
                  homeViewModel: HomeViewModel,
                  state: MoviesViewState
) {

    Log.w("TAG", "MoviesContent: " +state.isLoading )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { MyTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreens.FavoriteMoviesScreen.route)
                },
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp),
                containerColor = colorResource(id = R.color.primaryButtonColor),
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    "FavIcon"
                )
            }
        }

    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.primaryBackground))
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            LoadingAnimation(state.isLoading && state.movies.isNullOrEmpty())
           ListScreen(state, homeViewModel, navController)
        }



    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(state: MoviesViewState, viewModel: HomeViewModel, navController: NavController) {

    /*val movies by viewModel.getMoviesList().observeAsState(emptyList())*/
    var searchText by remember { mutableStateOf("") }

    //val isLoading by viewModel.isLoading.observeAsState(true)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading && !state.movies.isNullOrEmpty())

    /*LaunchedEffect(Unit) {
        viewModel.loadMovies()
    }*/


    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {

        SearchScreen(){query ->
            searchText = query
            viewModel.getMoviesByTitle(query)
            Log.w("TAG", "ListScreen: $query", )
        }
        Spacer(modifier = Modifier.height(20.dp))

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.loadRandomMovies()
                //viewModel.loadMovies()
            }) {
            LazyColumn {
                items(state.movies) { movie ->
                    MovieListCard(movie = movie, navController)
                }
            }
        }
        /*if (isLoading) {
            LoadingAnimation()
        } else {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.loadRandomMovies()
                    //viewModel.loadMovies()
                }) {
                LazyColumn {
                    items(movies) { movie ->
                        MovieItemListScreen.ListItemCard(movie = movie, navController)
                    }
                }
            }

        }*/
    }
}



@Preview
@Composable
fun preview(){
    val homeViewModel: HomeViewModel = hiltViewModel()
    val navController = NavController(LocalContext.current)
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    MoviesContent(navController,homeViewModel,state)
}