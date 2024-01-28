package com.hoopCarpool.movies.usescases.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.usescases.common.Components.MovieItemList
import com.hoopCarpool.movies.usescases.common.Components.SearchScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

val viewModel: HomeViewModel = viewModel()


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.homeTopBarTitle),
                    color = colorResource(
                        id = R.color.primaryTextColor
                    )
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = colorResource(id = R.color.primaryBackground)
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
                .padding(16.dp)
        ) {
            ListScreen(viewModel, navController)
        }
    }

}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(viewModel: HomeViewModel,navController: NavController) {
    val movies by viewModel.getMoviesList().observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    Column {

        SearchScreen(){query ->
            searchText = query
            viewModel.getMoviesByTitle(query)
            Log.w("TAG", "ListScreen: $query", )
        }
        Spacer(modifier = Modifier.height(5.dp))

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel::loadMovies
            }) {
            LazyColumn {
                items(movies) { movie ->
                    MovieItemList.ListItemCard(movie = movie, navController)
                }
            }
        }

        /*if (viewModel.getMoviesInmutable().isEmpty()) {
            LoadingAnimation()
        } else {
            // Display the list of movies

        }*/
    }
}

@Composable
fun LoadingAnimation() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}