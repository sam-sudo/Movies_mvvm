package com.hoopCarpool.movies.usescases.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
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
    val movies by viewModel.movies.observeAsState(emptyList())

    Column {

        SearchScreen()
        Spacer(modifier = Modifier.height(5.dp))

        if (movies.isEmpty()) {
            LoadingAnimation()
        } else {
            // Display the list of movies
            LazyColumn {
                items(movies) { movie ->
                    MovieItemList.ListItemCard(movie = movie)
                }
            }
        }
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