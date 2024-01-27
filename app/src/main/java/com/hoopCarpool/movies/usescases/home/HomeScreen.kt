package com.hoopCarpool.movies.usescases.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.usescases.common.Components.MovieItemList
import com.hoopCarpool.movies.usescases.common.Components.SearchScreen


@Composable
fun homeScreen(viewModel: HomeViewModel){
    Box (
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primaryBackground))
            .padding(16.dp)
    ){

        ListScreen(viewModel)
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(viewModel: HomeViewModel) {
    val movies by viewModel.getMoviesList().observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    Column {

        SearchScreen{query ->
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
                    MovieItemList.ListItemCard(movie = movie)
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
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}