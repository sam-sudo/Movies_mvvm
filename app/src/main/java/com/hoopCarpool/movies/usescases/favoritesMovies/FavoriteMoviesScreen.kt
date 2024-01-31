package com.hoopCarpool.movies.usescases.favoritesMovies

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hoopCarpool.movies.R
import com.hoopCarpool.movies.usescases.common.Components.movieDetail.MovieItemListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMoviesScreen( navController: NavController,  viewModel: FavoriteViewModel){
    val context = LocalContext.current



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { 
                Row (
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        "ArrowBack",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Text(
                        text = stringResource(id = R.string.favoritesTopBarTitle),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 20.dp)
                    )
                }
                
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = colorResource(id = R.color.primaryBackground)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        )

        FavoriteListScreen(viewModel = viewModel, navController = navController)

    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteListScreen(viewModel: FavoriteViewModel, navController: NavController) {
    val favoriteMovies by viewModel.favoriteMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadFavoriteMovies()
    }

    Log.w("TAG", "FavoriteListScreen: ${favoriteMovies}", )
    Column (
        modifier = Modifier
            .padding(top = 56.dp)
    ){

        if(favoriteMovies.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "no movie added favorites!",
                    color = colorResource(id = R.color.primaryTextColor),
                    fontSize = 20.sp
                )
            }
        }else{
            LazyColumn {
                items(favoriteMovies) { movie ->
                    MovieItemListScreen.ListItemCard(movie = movie, navController)
                }
            }
        }
    }
}

